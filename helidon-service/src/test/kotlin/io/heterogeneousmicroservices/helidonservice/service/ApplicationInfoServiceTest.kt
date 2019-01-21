package io.heterogeneousmicroservices.helidonservice.service

import com.orbitz.consul.Consul
import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.helidonservice.HelidonServiceApplication
import io.heterogeneousmicroservices.helidonservice.applicationContext
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.json.Json

internal class ApplicationInfoServiceTest : AutoCloseKoinTest() {

    private var server: WebServer? = null
    private val applicationInfoJsonService: ApplicationInfoJsonService by inject()

    // todo rewrite using coroutines
    @BeforeEach
    fun beforeEach() {
        startKoin(listOf(applicationContext))
        this.declareMock<Consul>()

        val startTimeout = 5000L // 5 seconds should be enough
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

    @AfterEach
    fun afterEach() {
        server?.shutdown()
            ?.toCompletableFuture()
            ?.get(10, TimeUnit.SECONDS)
    }

    @Test
    fun testGet() {
        val connection = getURLConnection("GET", "/application-info")
        assertEquals(200, connection.responseCode)

        val jsonReader = Json.createReader(connection.inputStream)
        val jsonObject = jsonReader.readObject()

        val expectedJsonObject = applicationInfoJsonService.getJsonObjectBuilder(
            ApplicationInfo("helidon-service", ApplicationInfo.Framework("Helidon SE", 2019), null)
        ).build()

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