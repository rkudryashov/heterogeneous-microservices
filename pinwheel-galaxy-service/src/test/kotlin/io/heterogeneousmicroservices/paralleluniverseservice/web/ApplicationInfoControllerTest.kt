package io.heterogeneousmicroservices.paralleluniverseservice.web

import io.heterogeneousmicroservices.paralleluniverseservice.model.ApplicationInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.reactivex.Single
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class ApplicationInfoControllerTest {

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

    // todo enable
    @Test
    @Disabled
    fun index() {
        val request: HttpRequest<Single<ApplicationInfo>> = HttpRequest.GET("/application-info")
        val body = client?.toBlocking()?.retrieve(request)
        assertNotNull(body)
        assertEquals("Hello World", body)
    }
}