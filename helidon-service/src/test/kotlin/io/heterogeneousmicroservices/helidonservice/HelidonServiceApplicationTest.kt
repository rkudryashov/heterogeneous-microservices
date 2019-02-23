package io.heterogeneousmicroservices.helidonservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.orbitz.consul.Consul
import io.helidon.common.configurable.Resource
import io.helidon.common.http.MediaType
import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

internal class HelidonServiceApplicationTest : AutoCloseKoinTest() {

    private var server: WebServer? = null

    // todo rewrite using coroutines
    // todo how to start server and koin once?
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
        stopKoin()
    }

    @Test
    fun testGet() {
        val connection = getURLConnection("GET", "/application-info")
        assertEquals(200, connection.responseCode)
        assertEquals(MediaType.APPLICATION_JSON.toString(), connection.contentType)

        val expected = ApplicationInfo("helidon-service", ApplicationInfo.Framework("Helidon SE", 2019), null)
        val actual = jacksonObjectMapper().readValue(connection.inputStream, ApplicationInfo::class.java)
        assertEquals(expected, actual)
    }

    @Test
    fun testGetLogo() {
        val connection = getURLConnection("GET", "/application-info/logo")
        assertEquals(200, connection.responseCode)
        assertEquals(MediaType.create("image", "png").toString(), connection.contentType)
        assertArrayEquals(Resource.create("logo.png").bytes(), connection.inputStream.readBytes())
    }

    private fun getURLConnection(method: String, path: String): HttpURLConnection {
        val url = URL("http://localhost:" + server?.port() + path)
        return (url.openConnection() as HttpURLConnection).apply {
            this.requestMethod = method
            this.setRequestProperty("Accept", "application/json")
        }
    }
}