package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.config.ApplicationInfoConfigurationProperties
import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.heterogeneousmicroservices.micronautservice.model.Projection
import javax.inject.Singleton

@Singleton
class ApplicationInfoService(
        private val applicationInfoConfigurationProperties: ApplicationInfoConfigurationProperties,
        private val springBootServiceClient: SpringBootServiceClient
) {

    fun get(projection: Projection): ApplicationInfo = ApplicationInfo(
            applicationInfoConfigurationProperties.name,
            ApplicationInfo.Framework(
                    applicationInfoConfigurationProperties.framework.name,
                    applicationInfoConfigurationProperties.framework.releaseYear.toInt()
            ),
            when (projection) {
                Projection.DEFAULT -> null
                Projection.FULL -> springBootServiceClient.getApplicationInfo()
            }
    )
}