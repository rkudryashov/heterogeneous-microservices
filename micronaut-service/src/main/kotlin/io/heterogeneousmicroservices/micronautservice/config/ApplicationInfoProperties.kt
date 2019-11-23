package io.heterogeneousmicroservices.micronautservice.config

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("application-info")
class ApplicationInfoProperties {

    lateinit var name: String
    lateinit var framework: FrameworkConfigurationProperties

    @ConfigurationProperties("framework")
    class FrameworkConfigurationProperties {
        lateinit var name: String
        lateinit var releaseYear: String
    }
}