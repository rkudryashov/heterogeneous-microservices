package io.heterogeneousmicroservices.quarkusservice.config

import io.quarkus.arc.config.ConfigProperties

@ConfigProperties(prefix = "application-info")
class ApplicationInfoProperties {

    lateinit var name: String

    lateinit var framework: FrameworkConfiguration

    class FrameworkConfiguration {
        lateinit var name: String
        lateinit var releaseYear: String
    }
}
