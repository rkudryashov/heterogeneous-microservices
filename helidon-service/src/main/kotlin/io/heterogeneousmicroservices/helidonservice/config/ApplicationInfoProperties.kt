package io.heterogeneousmicroservices.helidonservice.config

import io.helidon.config.Config

class ApplicationInfoProperties {

    companion object {
        const val applicationInfoKey = "application-info"
        const val nameKey = "name"
        const val frameworkKey = "framework"
        const val frameworkNameKey = "name"
        const val frameworkReleaseYearKey = "releaseYear"
        const val followingApplicationKey = "followingApplication"
    }

    private val applicationInfoConfig = Config.create().get(applicationInfoKey)
    private val frameworkConfig = applicationInfoConfig.get(frameworkKey)

    val name = applicationInfoConfig[nameKey].asString()
    val frameworkName = frameworkConfig[frameworkNameKey].asString()
    val frameworkReleaseDate = frameworkConfig[frameworkReleaseYearKey].asInt()
}