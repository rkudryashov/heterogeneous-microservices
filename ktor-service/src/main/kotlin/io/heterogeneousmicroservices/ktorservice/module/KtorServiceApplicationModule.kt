package io.heterogeneousmicroservices.ktorservice.module

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigUtil
import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.Projection
import io.heterogeneousmicroservices.ktorservice.service.ApplicationInfoService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.dsl.module.module
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

private val ktorDeploymentConfig: ApplicationConfig =
    HoconApplicationConfig(ConfigFactory.load().getConfig(ConfigUtil.joinPath("ktor", "deployment")))
private val port: Int = ktorDeploymentConfig.property("port").getString().toInt()

private val applicationContext = module {
    single { ApplicationInfoService(get(), get()) }
    single { ApplicationInfoProperties() }
    single { Consul.builder().withUrl("http://localhost:8500").build() }
}

// referenced in application.conf
fun Application.module() {
    startKoin(listOf(applicationContext))

    val applicationInfoService: ApplicationInfoService by inject()

    if (!isTest()) {
        val consulClient: Consul by inject()
        registerInConsul(applicationInfoService.get(Projection.DEFAULT).name, consulClient)
    }

    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {}
    }

    routing {
        get("/application-info{projection?}") {
            val requestProjection: String? = call.parameters["projection"]
            val projection =
                if (!requestProjection.isNullOrBlank()) Projection.valueOf(requestProjection.toUpperCase()) else Projection.DEFAULT
            call.respond(applicationInfoService.get(projection))
        }
    }
}

private fun registerInConsul(serviceName: String, consulClient: Consul) =
    consulClient.agentClient().register(buildConsulRegistration(serviceName))

private fun buildConsulRegistration(serviceName: String) = ImmutableRegistration.builder()
    .id("$serviceName-$port")
    .name(serviceName)
    .address("localhost")
    .port(port)
    .build()

private fun Application.isTest(): Boolean =
    environment.config.propertyOrNull("ktor.deployment.environment")?.getString().equals("test")