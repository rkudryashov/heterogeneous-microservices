package io.heterogeneousmicroservices.cartwheelgalaxyservice.service

import io.heterogeneousmicroservices.cartwheelgalaxyservice.config.GalaxyInfoConfigurationProperties
import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GalaxyInfoService(
        @Autowired private val galaxyInfoConfigurationProperties: GalaxyInfoConfigurationProperties
) {

    // todo implement projections
    fun get(): GalaxyInfo = GalaxyInfo(
            galaxyInfoConfigurationProperties.name,
            galaxyInfoConfigurationProperties.constellation,
            galaxyInfoConfigurationProperties.distance.toDouble(),
            listOf()
    )
}