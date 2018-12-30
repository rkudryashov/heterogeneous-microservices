package io.heterogeneousmicroservices.cartwheelgalaxyservice.web

import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
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
internal class GalaxyInfoControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testGet() {
        webTestClient
                .get().uri("/galaxy-info")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectStatus().isOk
                .expectBody(GalaxyInfo::class.java)
                .returnResult().apply {
                    MatcherAssert.assertThat(this.responseBody, Matchers.equalTo(GalaxyInfo("Cartwheel", "Sculptor", 489.2, null)))
                }
    }
}