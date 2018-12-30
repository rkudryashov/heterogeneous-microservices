package io.heterogeneousmicroservices.pinwheelgalaxyservice.web

import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class GalaxyInfoControllerTest {

    companion object {
        private var server: EmbeddedServer? = null
        private var client: HttpClient? = null

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            server = ApplicationContext.run(EmbeddedServer::class.java)
            client = server?.applicationContext?.createBean(HttpClient::class.java, server?.url) ?: throw IllegalStateException("Cannot get server instance")
        }

        @AfterAll
        @JvmStatic
        fun stopServer() {
            server?.stop()
            client?.stop()
        }
    }

    @Test
    fun testGet() {
        val responseBody: GalaxyInfo = client?.toBlocking()?.retrieve("/galaxy-info", GalaxyInfo::class.java)
                ?: throw IllegalStateException("Http client must be not null")
        assertNotNull(responseBody)
        val expected = GalaxyInfo("Pinwheel", "Ursa Major", 20.87, null)
        assertEquals(expected, responseBody)
    }
}