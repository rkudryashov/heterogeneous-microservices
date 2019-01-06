package io.heterogeneousmicroservices.ktorservice

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
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
import org.koin.dsl.module.module
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

val applicationContext = module {
    single { ApplicationInfoService(get()) }
    single { ApplicationInfoProperties() }
}

// referenced in application.conf
fun Application.module() {
    startKoin(listOf(applicationContext))

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