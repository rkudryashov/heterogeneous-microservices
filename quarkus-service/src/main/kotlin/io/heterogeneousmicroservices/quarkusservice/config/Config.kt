package io.heterogeneousmicroservices.quarkusservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Singleton

@ApplicationScoped
class Config {
    @Singleton
    @Produces
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
}