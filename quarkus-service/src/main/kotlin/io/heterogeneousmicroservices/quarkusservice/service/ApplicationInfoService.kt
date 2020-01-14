package io.heterogeneousmicroservices.quarkusservice.service

import io.heterogeneousmicroservices.quarkusservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.quarkusservice.model.ApplicationInfo
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ApplicationInfoService(
    @Inject private val applicationInfoProperties: ApplicationInfoProperties,
    @Inject private val serviceClient: ServiceClient
) {

    fun get(anotherServiceName: String?): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.framework.name,
            applicationInfoProperties.framework.releaseYear.toInt()
        ),
        anotherServiceName?.let { serviceClient.getApplicationInfo(it) }
    )

    fun getLogo(): ByteArray = javaClass.classLoader.getResourceAsStream("logo.png")?.readBytes()
        ?: throw RuntimeException("Can't find logo")
}