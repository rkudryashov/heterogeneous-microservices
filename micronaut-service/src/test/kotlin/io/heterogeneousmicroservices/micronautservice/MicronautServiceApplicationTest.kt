package io.heterogeneousmicroservices.micronautservice

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.context.ApplicationContext
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.io.ResourceResolver
import io.micronaut.core.io.scan.ClassPathResourceLoader
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
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
        val response: HttpResponse<ApplicationInfo> =
            client!!.toBlocking().exchange("/application-info", ApplicationInfo::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(MediaType.APPLICATION_JSON, response.contentType.get().name)
        val expected = ApplicationInfo("micronaut-service", ApplicationInfo.Framework("Micronaut", 2018), null)
        assertEquals(expected, response.body.get())
    }

    @Test
    fun testGetLogo() {
        val response: HttpResponse<ByteArray> =
            client!!.toBlocking().exchange("/application-info/logo", ByteArray::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(MediaType.IMAGE_PNG, response.contentType.get().name)
        val loader: ResourceLoader = ResourceResolver().getLoader(ClassPathResourceLoader::class.java).get()
        val expected = loader.getResource("classpath:logo.png").get().readBytes()
        assertArrayEquals(expected, response.body.get())
    }
}