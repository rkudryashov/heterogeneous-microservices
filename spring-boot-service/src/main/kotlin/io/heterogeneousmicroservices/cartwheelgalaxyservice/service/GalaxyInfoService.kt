package io.heterogeneousmicroservices.cartwheelgalaxyservice.service

import io.heterogeneousmicroservices.cartwheelgalaxyservice.config.GalaxyInfoConfigurationProperties
import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.Projection
import org.springframework.stereotype.Service

@Service
class GalaxyInfoService(
        private val galaxyInfoConfigurationProperties: GalaxyInfoConfigurationProperties,
        private val pinwheelGalaxyServiceClient: PinwheelGalaxyServiceClient
) {

    fun get(projection: Projection): GalaxyInfo = GalaxyInfo(
            galaxyInfoConfigurationProperties.name,
            galaxyInfoConfigurationProperties.constellation,
            galaxyInfoConfigurationProperties.distance.toDouble(),
            when (projection) {
                Projection.DEFAULT -> null
                Projection.FULL -> listOf(
                        pinwheelGalaxyServiceClient.getGalaxyInfo()
                )
            }
    )
}