package io.heterogeneousmicroservices.ktorservice.service

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.heterogeneousmicroservices.ktorservice.model.Projection

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties
) {

    // todo process projection
    fun get(projection: Projection) = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseYear
        ),
        null
    )
}