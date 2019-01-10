package io.heterogeneousmicroservices.helidonservice

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import io.helidon.common.http.Http
import io.helidon.config.Config
import io.helidon.webserver.NotFoundException
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerConfiguration
import io.helidon.webserver.WebServer
import io.helidon.webserver.json.JsonSupport
import io.heterogeneousmicroservices.helidonservice.service.ApplicationInfoService
import org.slf4j.LoggerFactory

object HelidonServiceApplication {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val applicationInfoService = ApplicationInfoService()
    private val consulClient = Consul.builder().withUrl("http://localhost:8500").build()

    @JvmStatic
    fun main(args: Array<String>) {
        startServer()
    }

    fun startServer(): WebServer {
        // read config from application.yaml
        val config = Config.create()
        val serverConfig = ServerConfiguration.fromConfig(config.get("server"))

        val server: WebServer = WebServer
            .builder(createRouting())
            .configuration(serverConfig)
            .build()

        server.start().thenAccept { ws ->
            log.info("Service running at: http://localhost:" + ws.port())
            val serviceName = applicationInfoService.applicationInfoProperties.name
            registerInConsul(serviceName, ws.port())
        }

        return server
    }

    private fun createRouting(): Routing {
        return Routing.builder()
            // add JSON support to all end-points
            .register(JsonSupport.get())
            .register("/application-info", applicationInfoService)
            .error(NotFoundException::class.java) { req, res, ex ->
                log.error("NotFoundException:", ex)
                res.status(Http.Status.BAD_REQUEST_400).send()
            }
            .error(Exception::class.java) { req, res, ex ->
                log.error("Exception:", ex)
                res.status(Http.Status.INTERNAL_SERVER_ERROR_500).send()
            }
            .build()
    }

    private fun registerInConsul(serviceName: String, port: Int) =
        consulClient.agentClient().register(buildConsulRegistration(serviceName, port))

    private fun buildConsulRegistration(serviceName: String, port: Int) = ImmutableRegistration.builder()
        .id("$serviceName-$port")
        .name(serviceName)
        .address("localhost")
        .port(port)
        .build()
}
