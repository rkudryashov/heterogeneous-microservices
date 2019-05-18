package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.config.ApplicationInfoConfigurationProperties
import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class ApplicationInfoService(
    private val applicationInfoConfigurationProperties: ApplicationInfoConfigurationProperties,
    private val serviceClient: ServiceClient
) {

    fun get(anotherServiceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoConfigurationProperties.name,
        ApplicationInfo.Framework(
            applicationInfoConfigurationProperties.framework.name,
            applicationInfoConfigurationProperties.framework.releaseYear.toInt()
        ),
        anotherServiceName?.let { serviceClient.getApplicationInfo(anotherServiceName) }
    )

    fun getLogo(): ByteArray = ClassPathResource("logo.png").inputStream.readBytes()
}