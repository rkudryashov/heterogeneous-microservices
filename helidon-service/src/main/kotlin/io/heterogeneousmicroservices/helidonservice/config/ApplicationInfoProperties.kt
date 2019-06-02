package io.heterogeneousmicroservices.helidonservice.config

import io.helidon.config.Config
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo

class ApplicationInfoProperties(

    private val applicationInfo: ApplicationInfo = Config.create()
        .get("application-info").`as` {
            ApplicationInfo(
                it["name"].asString().get(),
                ApplicationInfo.Framework(
                    it["framework"]["name"].asString().get(),
                    it["framework"]["release-year"].asInt().get()
                ),
                null
            )
        }.orElseThrow { IllegalStateException("Cannot parse config") },

    val name: String = applicationInfo.name,
    val frameworkName: String = applicationInfo.framework.name,
    val frameworkReleaseYear: Int = applicationInfo.framework.releaseYear
)