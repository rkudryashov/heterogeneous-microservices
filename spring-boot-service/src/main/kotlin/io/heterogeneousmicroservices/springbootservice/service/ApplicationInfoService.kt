package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties,
    private val serviceClient: ServiceClient
) {

    fun get(anotherServiceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.framework.name,
            applicationInfoProperties.framework.releaseYear.toInt()
        ),
        anotherServiceName?.let { serviceClient.getApplicationInfo(it) }
    )

    fun getLogo(): ByteArray = ClassPathResource("logo.png").inputStream.readBytes()
}