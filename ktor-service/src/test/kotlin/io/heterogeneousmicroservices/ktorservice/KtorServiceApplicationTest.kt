package io.heterogeneousmicroservices.ktorservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.orbitz.consul.Consul
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.heterogeneousmicroservices.ktorservice.module.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock

internal class KtorServiceApplicationTest : AutoCloseKoinTest() {

    @Test
    fun testGet(): Unit = withTestApplication(Application::module) {
        startKoin(listOf(applicationContext))
        declareMock<Consul>()
        val mapper = jacksonObjectMapper()
        handleRequest(HttpMethod.Get, "/application-info").apply {
            assertEquals(200, response.status()?.value)
            val expected = ApplicationInfo(
                "ktor-service",
                ApplicationInfo.Framework("Ktor", 2018),
                null
            )
            assertEquals(
                expected,
                mapper.readValue<ApplicationInfo>(
                    response.content ?: throw IllegalStateException("No content found in response")
                )
            )
        }
    }
}