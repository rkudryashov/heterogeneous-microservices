package io.heterogeneousmicroservices.micronautservice

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.io.ResourceResolver
import io.micronaut.core.io.scan.ClassPathResourceLoader
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class MicronautServiceApplicationTest {

    companion object {
        private var server: EmbeddedServer? = null
        private var client: HttpClient? = null

        @BeforeAll
        @JvmStatic
        fun startServer() {
            server = ApplicationContext.run(EmbeddedServer::class.java)
            client = server?.applicationContext?.createBean(HttpClient::class.java, server?.url)
                ?: throw IllegalStateException("Cannot get server instance")
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
        val responseBody: ApplicationInfo = client?.toBlocking()
            ?.retrieve("/application-info", ApplicationInfo::class.java)
            ?: throw IllegalStateException("Http client must be not null")
        assertNotNull(responseBody)
        val expected = ApplicationInfo("micronaut-service", ApplicationInfo.Framework("Micronaut", 2018), null)
        assertEquals(expected, responseBody)
    }

    @Test
    @Disabled
    fun testGetLogo() {
        val responseBody: ByteArray = client?.toBlocking()
            ?.retrieve("/application-info/logo", ByteArray::class.java)
            ?: throw IllegalStateException("Http client must be not null")
        assertNotNull(responseBody)
        val loader: ResourceLoader = ResourceResolver().getLoader(ClassPathResourceLoader::class.java).get()
        val expected = loader.getResource("classpath:logo.png").get().readBytes()
        assertEquals(expected, responseBody)
    }
}