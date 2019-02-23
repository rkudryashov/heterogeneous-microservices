package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.common.configurable.Resource
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import io.heterogeneousmicroservices.helidonservice.model.Projection

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties,
    private val ktorServiceClient: KtorServiceClient
) {

    fun get(projection: Projection): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseDate
        ),
        when (projection) {
            Projection.DEFAULT -> null
            Projection.FULL -> ktorServiceClient.getApplicationInfo()
        }
    )

    fun getLogo(): ByteArray = Resource.create("logo.png").bytes()
}