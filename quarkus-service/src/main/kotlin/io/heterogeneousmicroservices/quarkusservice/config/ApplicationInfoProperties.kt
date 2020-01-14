package io.heterogeneousmicroservices.quarkusservice.config

import io.quarkus.arc.config.ConfigProperties

@ConfigProperties(prefix = "application-info")
class ApplicationInfoProperties {
    // we can't inject `val name: String` in constructor due to the following error:
    // Class `ApplicationInfoProperties` which is annotated with `ConfigProperties` must contain a no-arg constructor
    lateinit var name: String

    lateinit var framework: FrameworkConfiguration

    class FrameworkConfiguration {
        lateinit var name: String
        lateinit var releaseYear: String
    }
}