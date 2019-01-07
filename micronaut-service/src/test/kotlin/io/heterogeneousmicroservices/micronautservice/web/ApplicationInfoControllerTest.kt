package io.heterogeneousmicroservices.micronautservice.web

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class ApplicationInfoControllerTest {

    companion object {
        private var server: EmbeddedServer? = null
        private var client: HttpClient? = null

        @BeforeAll
        @JvmStatic
        fun startServer() {
            server = ApplicationContext.run(EmbeddedServer::class.java)
            client = server?.applicationContext?.createBean(HttpClient::class.java, server?.url) ?:
                    throw IllegalStateException("Cannot get server instance")
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
        val responseBody: ApplicationInfo =
            client?.toBlocking()?.retrieve("/application-info", ApplicationInfo::class.java)
                ?: throw IllegalStateException("Http client must be not null")
        assertNotNull(responseBody)
        val expected = ApplicationInfo("micronaut-service", ApplicationInfo.Framework("Micronaut", 2018), null)
        assertEquals(expected, responseBody)
    }
}