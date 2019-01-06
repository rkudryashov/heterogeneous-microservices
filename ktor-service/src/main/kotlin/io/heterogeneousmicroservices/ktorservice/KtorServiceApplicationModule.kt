package io.heterogeneousmicroservices.ktorservice

import io.heterogeneousmicroservices.ktorservice.model.Projection
import io.heterogeneousmicroservices.ktorservice.service.ApplicationInfoService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {}
    }

    val applicationInfoService: ApplicationInfoService by inject()
    routing {
        get("/application-info") {
            // todo process projection
            call.respond(applicationInfoService.get(Projection.DEFAULT))
        }
    }
}