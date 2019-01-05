package io.heterogeneousmicroservices.helidonseservice.service

import io.heterogeneousmicroservices.helidonseservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonseservice.model.ApplicationInfo
import javax.json.Json
import javax.json.JsonObjectBuilder

class ApplicationInfoJsonService {

    private val jsonBuilderFactory = Json.createBuilderFactory(null)

    fun getJsonObjectBuilder(applicationInfo: ApplicationInfo): JsonObjectBuilder = jsonBuilderFactory.createObjectBuilder()
            .add(ApplicationInfoProperties.nameKey, applicationInfo.name)
            .add(ApplicationInfoProperties.frameworkKey, jsonBuilderFactory.createObjectBuilder()
                    .add(ApplicationInfoProperties.frameworkNameKey, applicationInfo.framework.name)
                    .add(ApplicationInfoProperties.frameworkReleaseYearKey, applicationInfo.framework.releaseYear))
            .add(ApplicationInfoProperties.followingApplicationKey, applicationInfo.followingApplication
                    ?.let { getJsonObjectBuilder(it) } ?: jsonBuilderFactory.createObjectBuilder())
}