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
    single { ApplicationInfoService(get()) }
    single { ApplicationInfoProperties() }
}

// referenced in application.conf
fun Application.module() {
    startKoin(listOf(applicationContext))
    val applicationInfoService: ApplicationInfoService by inject()
    val serviceName = applicationInfoService.get(Projection.DEFAULT).name
    if (!isTest()) registerInConsul(serviceName)

    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {}
    }

    routing {
        get("/application-info") {
            // todo process projection
            call.respond(applicationInfoService.get(Projection.DEFAULT))
        }
    }
}

private fun registerInConsul(serviceName: String) {
    val consulClient = Consul.builder().withUrl("http://localhost:8500").build()
    val service = ImmutableRegistration.builder()
        .id("$serviceName-$port")
        .name(serviceName)
        .address("localhost")
        .port(port)
        .build()

    consulClient.agentClient().register(service)
}

private fun Application.isTest(): Boolean =
    environment.config.propertyOrNull("ktor.deployment.environment")?.getString().equals("test")