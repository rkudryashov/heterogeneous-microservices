package io.heterogeneousmicroservices.paralleluniverseservice.service

import io.heterogeneousmicroservices.paralleluniverseservice.model.ApplicationInfo
import io.heterogeneousmicroservices.paralleluniverseservice.web.UniverseServiceClient
import io.micronaut.context.annotation.Value
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationInfoService(
        // todo rename
        @Inject private val universeServiceClient: UniverseServiceClient,
        @Value("\${micronaut.application.name}") private val applicationName: String
) {

    fun get(): ApplicationInfo {
        // todo rename
        val universeServiceApplicationInfo: ApplicationInfo = universeServiceClient.getApplicationInfo()
        return ApplicationInfo(applicationName, listOf(universeServiceApplicationInfo))
    }
}