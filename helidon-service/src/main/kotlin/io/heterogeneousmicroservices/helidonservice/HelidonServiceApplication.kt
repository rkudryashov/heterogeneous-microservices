package io.heterogeneousmicroservices.helidonservice

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import io.helidon.common.http.Http
import io.helidon.common.http.MediaType
import io.helidon.config.Config
import io.helidon.media.jackson.common.JacksonSupport
import io.helidon.webserver.Handler
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerConfiguration
import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.service.ApplicationInfoService
import io.heterogeneousmicroservices.helidonservice.service.ServiceClient
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.slf4j.LoggerFactory

val koinModule = module {
    single { ApplicationInfoService(get(), get()) }
    single { ApplicationInfoProperties() }
    single { ServiceClient(get()) }
    single { Consul.builder().withUrl("http://localhost:8500").build() }
}

private val log = LoggerFactory.getLogger(HelidonServiceApplication::class.java)

object HelidonServiceApplication : KoinComponent {

    @JvmStatic
    fun main(args: Array<String>) {
        val startTime = System.currentTimeMillis()
        startKoin {
            modules(koinModule)
        }

        val applicationInfoService: ApplicationInfoService by inject()
        val consulClient: Consul by inject()
        val applicationInfoProperties: ApplicationInfoProperties by inject()
        val serviceName = applicationInfoProperties.name

        startServer(applicationInfoService, consulClient, serviceName, startTime)
    }
}

fun startServer(
    applicationInfoService: ApplicationInfoService,
    consulClient: Consul,
    serviceName: String,
    startTime: Long
): WebServer {
    val serverConfig = ServerConfiguration.create(Config.create().get("webserver"))

    val server: WebServer = WebServer
        .builder(createRouting(applicationInfoService))
        .addMediaSupport(JacksonSupport.create())
        .config(serverConfig)
        .build()

    server.start().thenAccept { ws ->
        val durationInMillis = System.currentTimeMillis() - startTime
        log.info("Startup completed in $durationInMillis ms. Service running at: http://localhost:" + ws.port())
        // register in Consul
        consulClient.agentClient().register(createConsulRegistration(serviceName, ws.port()))
    }

    return server
}

private fun createRouting(applicationInfoService: ApplicationInfoService) = Routing.builder()
    .get("/application-info", Handler { req, res ->
        val requestTo: String? = req.queryParams()
            .first("request-to")
            .orElse(null)

        res
            .status(Http.ResponseStatus.create(200))
            .send(applicationInfoService.get(requestTo))
    })
    .get("/application-info/logo", Handler { req, res ->
        res.headers().contentType(MediaType.create("image", "png"))
        res
            .status(Http.ResponseStatus.create(200))
            .send(applicationInfoService.getLogo())
    })
    .error(Exception::class.java) { req, res, ex ->
        log.error("Exception:", ex)
        res.status(Http.Status.INTERNAL_SERVER_ERROR_500).send()
    }
    .build()

private fun createConsulRegistration(serviceName: String, port: Int) = ImmutableRegistration.builder()
    .id("$serviceName-$port")
    .name(serviceName)
    .address("localhost")
    .port(port)
    .build()
