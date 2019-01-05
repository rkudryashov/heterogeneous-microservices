package io.heterogeneousmicroservices.pinwheelgalaxyservice.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("galaxy-info")
class GalaxyInfoConfigurationProperties {

    lateinit var name: String
    lateinit var constellation: String
    lateinit var distance: String
}