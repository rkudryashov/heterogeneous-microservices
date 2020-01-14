package io.heterogeneousmicroservices.helidonservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.orbitz.consul.Consul
import io.helidon.common.configurable.Resource
import io.helidon.common.http.MediaType
import io.helidon.webserver.WebServer
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import io.heterogeneousmicroservices.helidonservice.service.ApplicationInfoService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.mock.declareMock
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class HelidonServiceApplicationTest : AutoCloseKoinTest() {

    private var server: WebServer? = null

    // todo how to start server and koin once?
    @BeforeEach
    fun beforeEach() {
        startKoin {
            modules(koinModule)
        }
        this.declareMock<Consul>()
        val applicationInfoService: ApplicationInfoService by inject()
        val consulClient: Consul by inject()
        val applicationInfoProperties: ApplicationInfoProperties by inject()

        val startTimeout = 30000L // 30 seconds should be enough
        val startTime = System.currentTimeMillis()

        server = startServer(applicationInfoService, consulClient, applicationInfoProperties.name, startTime)
        server?.let {
            while (!it.isRunning) {
                Thread.sleep(100)
                check(System.currentTimeMillis() - startTime <= startTimeout) { "Webserver haven't been started" }
            }
        }
    }

    @AfterEach
    fun afterEach() {
        server?.shutdown()
            ?.toCompletableFuture()
            ?.get(5, TimeUnit.SECONDS)
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