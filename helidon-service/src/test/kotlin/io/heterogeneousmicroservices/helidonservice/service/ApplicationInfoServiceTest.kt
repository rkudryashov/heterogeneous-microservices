package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.helidonservice.HelidonServiceApplication
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.json.Json

internal class ApplicationInfoServiceTest {

    companion object {
        private var server: WebServer? = null

        @BeforeAll
        @JvmStatic
        // todo rewrite using coroutines
        fun startServer() {
            val startTimeout = 4000L // 4 seconds should be enough
            val startTime = System.currentTimeMillis()

            server = HelidonServiceApplication.startServer()
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
                ?.get(10, TimeUnit.SECONDS)
        }
    }

    private val applicationInfoJsonService = ApplicationInfoJsonService()

    @Test
    fun testGet() {
        val connection = getURLConnection("GET", "/application-info")
        assertEquals(200, connection.responseCode)

        val jsonReader = Json.createReader(connection.inputStream)
        val jsonObject = jsonReader.readObject()

        val expectedJsonObject = applicationInfoJsonService.getJsonObjectBuilder(
            ApplicationInfo(
                "helidon-service",
                ApplicationInfo.Framework("Helidon MP", 2019), null
            )
        )
            .build()

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