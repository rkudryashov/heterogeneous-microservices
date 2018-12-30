package io.heterogeneousmicroservices.pinwheelgalaxyservice.service

import io.heterogeneousmicroservices.pinwheelgalaxyservice.config.GalaxyInfoConfigurationProperties
import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.Projection
import javax.inject.Singleton

@Singleton
class GalaxyInfoService(
        private val galaxyInfoConfigurationProperties: GalaxyInfoConfigurationProperties,
        private val cartwheelGalaxyServiceClient: CartwheelGalaxyServiceClient
) {

    fun get(projection: Projection): GalaxyInfo = GalaxyInfo(
            galaxyInfoConfigurationProperties.name,
            galaxyInfoConfigurationProperties.constellation,
            galaxyInfoConfigurationProperties.distance.toDouble(),
            when (projection) {
                Projection.DEFAULT -> null
                Projection.FULL -> listOf(
                        cartwheelGalaxyServiceClient.getGalaxyInfo()
                )
            }
    )
}