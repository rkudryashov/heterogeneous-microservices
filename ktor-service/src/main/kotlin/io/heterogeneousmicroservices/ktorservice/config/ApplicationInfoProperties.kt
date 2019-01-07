package io.heterogeneousmicroservices.ktorservice.config

import com.typesafe.config.ConfigFactory
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig

class ApplicationInfoProperties(
    private val applicationInfoConfig: ApplicationConfig = HoconApplicationConfig(ConfigFactory.load().getConfig("application-info")),
    private val frameworkConfig: ApplicationConfig = applicationInfoConfig.config("framework"),
    val name: String = applicationInfoConfig.property("name").getString(),
    val frameworkName: String = frameworkConfig.property("name").getString(),
    val frameworkReleaseYear: Int = frameworkConfig.property("release-year").getString().toInt()
)