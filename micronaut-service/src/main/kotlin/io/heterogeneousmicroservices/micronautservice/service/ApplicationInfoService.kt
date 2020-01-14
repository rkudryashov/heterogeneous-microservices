package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.io.ResourceResolver
import io.micronaut.core.io.scan.ClassPathResourceLoader
import javax.inject.Singleton

@Singleton
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

    fun getLogo(): ByteArray {
        val loader: ResourceLoader = ResourceResolver().getLoader(ClassPathResourceLoader::class.java).get()
        return loader.getResource("classpath:logo.png").get().readBytes()
    }
}