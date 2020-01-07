package io.heterogeneousmicroservices.quarkusservice

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test

@QuarkusTest
class QuarkusServiceApplicationTest {

    @Test
    fun testGet() {
        given()
            .`when`().get("/application-info")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name") { `is`("quarkus-service") }
            .body("framework.name") { `is`("Quarkus") }
            .body("framework.releaseYear") { `is`(2019) }
    }

    @Test
    fun testGetLogo() {
        given()
            .`when`().get("/application-info/logo")
            .then()
            .statusCode(200)
            .contentType("image/png")
            .body(`is`(notNullValue()))
    }
}