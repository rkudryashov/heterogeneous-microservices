package io.heterogeneousmicroservices.pinwheelgalaxyservice.service

import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.pinwheelgalaxyservice.web.CartwheelGalaxyServiceClient
import io.micronaut.context.annotation.Value
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalaxyInfoService(
        @Inject private val cartwheelGalaxyServiceClient: CartwheelGalaxyServiceClient,
        @Value("\${micronaut.application.name}") private val applicationName: String
) {

    fun get(): GalaxyInfo {
        val cartwheelGalaxyInfo: GalaxyInfo = cartwheelGalaxyServiceClient.getGalaxyInfo()
        return GalaxyInfo(applicationName, listOf(cartwheelGalaxyInfo))
    }
}