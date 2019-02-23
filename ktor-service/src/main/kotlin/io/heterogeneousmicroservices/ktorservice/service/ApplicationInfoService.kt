package io.heterogeneousmicroservices.ktorservice.service

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.heterogeneousmicroservices.ktorservice.model.Projection
import kotlinx.coroutines.runBlocking

class ApplicationInfoService(
    private val micronautServiceClient: MicronautServiceClient,
    private val applicationInfoProperties: ApplicationInfoProperties
) {

    fun get(projection: Projection) = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseYear
        ),
        when (projection) {
            Projection.DEFAULT -> null
            Projection.FULL -> runBlocking {
                micronautServiceClient.getFollowingApplicationInfo()
            }
        }
    )
}