package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.config.ApplicationInfoConfigurationProperties
import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.heterogeneousmicroservices.micronautservice.model.Projection
import io.micronaut.core.io.ResourceLoader
import io.micronaut.core.io.ResourceResolver
import io.micronaut.core.io.scan.ClassPathResourceLoader
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

    fun getLogo(): ByteArray {
        val loader: ResourceLoader = ResourceResolver().getLoader(ClassPathResourceLoader::class.java).get()
        return loader.getResource("classpath:logo.png").get().readBytes()
    }
}