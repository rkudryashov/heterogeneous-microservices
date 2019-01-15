package io.heterogeneousmicroservices.helidonservice.config

import io.helidon.config.Config
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo

class ApplicationInfoProperties {

    companion object {
        const val applicationInfoKey = "application-info"
        const val nameKey = "name"
        const val frameworkKey = "framework"
        const val frameworkNameKey = "name"
        const val frameworkReleaseYearKey = "release-year"
        const val frameworkReleaseYearJsonKey = "releaseYear"
        const val followingApplicationKey = "followingApplication"
    }

    private val applicationInfo = Config.create()
        .get(applicationInfoKey).`as` {
            ApplicationInfo(
                it[nameKey].asString().get(),
                ApplicationInfo.Framework(
                    it[frameworkKey][frameworkNameKey].asString().get(),
                    it[frameworkKey][frameworkReleaseYearKey].asInt().get()
                ),
                null
            )
        }.orElseThrow { IllegalStateException("Cannot parse config") }

    val name = applicationInfo.name
    val frameworkName = applicationInfo.framework.name
    val frameworkReleaseDate = applicationInfo.framework.releaseYear
}