package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.config.ApplicationInfoConfigurationProperties
import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import io.heterogeneousmicroservices.springbootservice.model.Projection
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class ApplicationInfoService(
    private val applicationInfoConfigurationProperties: ApplicationInfoConfigurationProperties,
    private val helidonServiceClient: HelidonServiceClient
) {

    fun get(projection: Projection): ApplicationInfo = ApplicationInfo(
        applicationInfoConfigurationProperties.name,
        ApplicationInfo.Framework(
            applicationInfoConfigurationProperties.framework.name,
            applicationInfoConfigurationProperties.framework.releaseYear.toInt()
        ),
        when (projection) {
            Projection.DEFAULT -> null
            Projection.FULL -> helidonServiceClient.getApplicationInfo()
        }
    )

    fun getLogo(): ByteArray = ClassPathResource("logo.png").inputStream.readBytes()
}