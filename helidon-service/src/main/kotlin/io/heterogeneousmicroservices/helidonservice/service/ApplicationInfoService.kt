package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.common.configurable.Resource
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties,
    private val serviceClient: ServiceClient
) {

    fun get(serviceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseDate
        ),
        if (serviceName == null)
            null
        else
            serviceClient.getApplicationInfo(serviceName)
    )

    fun getLogo(): ByteArray = Resource.create("logo.png").bytes()
}