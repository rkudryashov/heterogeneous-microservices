package io.heterogeneousmicroservices.ktorservice.service

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo

class ApplicationInfoService(
    private val serviceClient: ServiceClient,
    private val applicationInfoProperties: ApplicationInfoProperties
) {

    fun get(anotherServiceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseYear
        ),
        if (anotherServiceName == null)
            null
        else
            serviceClient.getApplicationInfo(anotherServiceName)
    )
}