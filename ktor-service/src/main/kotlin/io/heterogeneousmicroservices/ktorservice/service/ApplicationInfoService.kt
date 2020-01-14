package io.heterogeneousmicroservices.ktorservice.service

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo

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
}