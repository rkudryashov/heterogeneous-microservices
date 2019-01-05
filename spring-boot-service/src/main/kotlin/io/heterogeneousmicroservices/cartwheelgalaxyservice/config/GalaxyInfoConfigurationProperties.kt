package io.heterogeneousmicroservices.cartwheelgalaxyservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("galaxy-info")
class GalaxyInfoConfigurationProperties {

    lateinit var name: String
    lateinit var constellation: String
    lateinit var distance: String
}