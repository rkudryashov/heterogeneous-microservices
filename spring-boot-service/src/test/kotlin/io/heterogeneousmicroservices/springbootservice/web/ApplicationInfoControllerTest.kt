package io.heterogeneousmicroservices.springbootservice.web

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ApplicationInfoControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testGet() {
        webTestClient
            .get().uri("/application-info")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
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
}