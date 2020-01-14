package io.heterogeneousmicroservices.springbootservice

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootServiceApplicationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testGet() {
        webTestClient
            .get().uri("/application-info")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody(ApplicationInfo::class.java)
            .returnResult().apply {
                MatcherAssert.assertThat(
                    this.responseBody, Matchers.equalTo(
                        ApplicationInfo(
                            "spring-boot-service",
                            ApplicationInfo.Framework("Spring Boot", 2014),
                            null
                        )
                    )
                )
            }
    }

    @Test
    fun testGetLogo() {
        webTestClient
            .get().uri("/application-info/logo")
            .exchange()
            .expectHeader().contentType(MediaType.IMAGE_PNG_VALUE)
            .expectStatus().isOk
            .expectBody(ByteArray::class.java)
            .returnResult().apply {
                assertArrayEquals(ClassPathResource("logo.png").inputStream.readBytes(), this.responseBody)
            }
    }
}