package io.heterogeneousmicroservices.pinwheelgalaxyservice.service

import io.heterogeneousmicroservices.pinwheelgalaxyservice.config.GalaxyInfoConfigurationProperties
import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import javax.inject.Singleton

@Singleton
class GalaxyInfoService(
        private val galaxyInfoConfigurationProperties: GalaxyInfoConfigurationProperties
) {

    // todo implement projections
    fun get(): GalaxyInfo = GalaxyInfo(
            galaxyInfoConfigurationProperties.name,
            galaxyInfoConfigurationProperties.constellation,
            galaxyInfoConfigurationProperties.distance.toDouble(),
            listOf()
    )
}