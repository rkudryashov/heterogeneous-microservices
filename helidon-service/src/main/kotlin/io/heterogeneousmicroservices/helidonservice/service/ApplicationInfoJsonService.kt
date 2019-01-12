package io.heterogeneousmicroservices.helidonservice.service

import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import javax.json.Json
import javax.json.JsonObjectBuilder

class ApplicationInfoJsonService {

    private val jsonBuilderFactory = Json.createBuilderFactory(null)

    fun getJsonObjectBuilder(applicationInfo: ApplicationInfo): JsonObjectBuilder =
        jsonBuilderFactory.createObjectBuilder()
            .add(ApplicationInfoProperties.nameKey, applicationInfo.name)
            .add(
                ApplicationInfoProperties.frameworkKey, jsonBuilderFactory.createObjectBuilder()
                    .add(ApplicationInfoProperties.frameworkNameKey, applicationInfo.framework.name)
                    .add(ApplicationInfoProperties.frameworkReleaseYearKey, applicationInfo.framework.releaseYear)
            ).also { builder ->
                applicationInfo.followingApplication?.let {
                    builder.add(ApplicationInfoProperties.followingApplicationKey, getJsonObjectBuilder(it))
                }
            }
}