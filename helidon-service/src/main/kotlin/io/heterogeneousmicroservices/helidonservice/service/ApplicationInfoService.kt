package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.common.configurable.Resource
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties,
    private val serviceClient: ServiceClient
) {

    fun get(anotherServiceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseYear
        ),
        anotherServiceName?.let { serviceClient.getApplicationInfo(it) }
    )

    fun getLogo(): ByteArray = Resource.create("logo.png").bytes()
}