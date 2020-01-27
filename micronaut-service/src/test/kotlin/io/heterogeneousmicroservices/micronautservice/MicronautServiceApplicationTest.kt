package io.heterogeneousmicroservices.micronautservice

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.io.ResourceResolver
import io.micronaut.core.io.scan.ClassPathResourceLoader
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class MicronautServiceApplicationTest {

    @Inject
    @field: Client("/")
    private lateinit var client: HttpClient

    @Test
    fun testGet() {
        val response: HttpResponse<ApplicationInfo> =
            client.toBlocking().exchange("/application-info", ApplicationInfo::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(MediaType.APPLICATION_JSON, response.contentType.get().name)
        val expected = ApplicationInfo("micronaut-service", ApplicationInfo.Framework("Micronaut", 2018), null)
        assertEquals(expected, response.body.get())
    }

    @Test
    fun testGetLogo() {
        val response: HttpResponse<ByteArray> =
            client.toBlocking().exchange("/application-info/logo", ByteArray::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertEquals(MediaType.IMAGE_PNG, response.contentType.get().name)
        val loader: ResourceLoader = ResourceResolver().getLoader(ClassPathResourceLoader::class.java).get()
        val expected = loader.getResource("classpath:logo.png").get().readBytes()
        assertArrayEquals(expected, response.body.get())
    }
}
