package io.heterogeneousmicroservices.cartwheelgalaxyservice.service

import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GalaxyInfoService(
        @Value("\${spring.application.name}") private val applicationName: String
) {

    // todo implement projections
    fun get(): GalaxyInfo = GalaxyInfo(applicationName, listOf())
}