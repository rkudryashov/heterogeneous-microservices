package io.heterogeneousmicroservices.springbootservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("application-info")
class ApplicationInfoProperties {

    lateinit var name: String
    val framework = FrameworkConfigurationProperties()

    class FrameworkConfigurationProperties {
        lateinit var name: String
        lateinit var releaseYear: String
    }
}