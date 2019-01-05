package io.heterogeneousmicroservices.triangulumgalaxyservice.web

import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.triangulumgalaxyservice.TriangulumGalaxyServiceApplication
import io.heterogeneousmicroservices.triangulumgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.triangulumgalaxyservice.service.GalaxyInfoJsonService
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.json.Json

internal class GalaxyInfoControllerTest {

    companion object {
        private var server: WebServer? = null

        @BeforeAll
        @JvmStatic
        fun startServer() {
            val startTimeout = 2000L // 2 seconds should be enough
            val startTime = System.currentTimeMillis()

            server = TriangulumGalaxyServiceApplication.startServer()
            server?.let {
                while (!it.isRunning) {
                    Thread.sleep(100)
                    if (System.currentTimeMillis() - startTime > startTimeout) {
                        throw IllegalStateException("Webserver haven't been started")
                    }
                }
            }
        }

        @AfterAll
        @JvmStatic
        fun stopServer() {
            server?.shutdown()
                    ?.toCompletableFuture()
                    ?.get(10, TimeUnit.SECONDS);
        }
    }

    private val galaxyInfoJsonService = GalaxyInfoJsonService()

    @Test
    fun testGet() {
        val connection = getURLConnection("GET", "/galaxy-info")
        assertEquals(200, connection.responseCode)
        val jsonReader = Json.createReader(connection.inputStream)
        val jsonObject = jsonReader.readObject()
        val expectedJsonObject = galaxyInfoJsonService.getJsonObjectBuilder(GalaxyInfo("Triangulum", "Triangulum", 2.723, null)).build()
        assertEquals(expectedJsonObject, jsonObject)
    }

    private fun getURLConnection(method: String, path: String): HttpURLConnection {
        val url = URL("http://localhost:" + server?.port() + path)
        return (url.openConnection() as HttpURLConnection).apply {
            this.requestMethod = method
            this.setRequestProperty("Accept", "application/json")
        }
    }
}