package io.heterogeneousmicroservices.helidonservice

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
        }

        return server
    }

    private fun createRouting(): Routing {
        return Routing.builder()
            // add JSON support to all end-points
            .register(JsonSupport.get())
            .register("/application-info", ApplicationInfoService())
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
}
