package io.heterogeneousmicroservices.ktorservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.orbitz.consul.Consul
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.heterogeneousmicroservices.ktorservice.module.module
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.mock.declareMock
import java.io.File

class KtorServiceApplicationTest : AutoCloseKoinTest() {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {
            startKoin {
                modules(koinModule)
            }
        }
    }

    // todo remove read `env` property for test purpose
    @Test
    fun testGet() {
        withTestApplication(Application::module) {
            declareMock<Consul>()
            val objectMapper = jacksonObjectMapper()
            handleRequest(HttpMethod.Get, "/application-info").apply {
                assertEquals(200, response.status()?.value)
                assertEquals(ContentType.Application.Json, response.contentType().withoutParameters())
                val expected = ApplicationInfo(
                    "ktor-service", ApplicationInfo.Framework("Ktor", 2018), null
                )
                val actual = objectMapper.readValue<ApplicationInfo>(
                    response.content ?: throw IllegalStateException("No content found in response")
                )
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun testGetLogo() {
        withTestApplication(Application::module) {
            declareMock<Consul>()
            handleRequest(HttpMethod.Get, "/application-info/logo").apply {
                assertEquals(200, response.status()?.value)
                assertEquals(ContentType.Image.PNG, response.contentType())
                val expected = File("src/main/resources/logo.png").readBytes()
                assertArrayEquals(expected, response.byteContent)
            }
        }
    }
}