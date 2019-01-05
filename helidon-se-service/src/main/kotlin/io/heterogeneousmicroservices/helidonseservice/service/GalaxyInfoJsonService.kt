package io.heterogeneousmicroservices.helidonseservice.service

import io.heterogeneousmicroservices.helidonseservice.config.GalaxyInfoProperties
import io.heterogeneousmicroservices.helidonseservice.model.GalaxyInfo
import javax.json.Json
import javax.json.JsonObjectBuilder

class GalaxyInfoJsonService {

    private val jsonBuilderFactory = Json.createBuilderFactory(null)

    fun getJsonObjectBuilder(galaxyInfo: GalaxyInfo): JsonObjectBuilder = jsonBuilderFactory.createObjectBuilder()
            .add(GalaxyInfoProperties.nameKey, galaxyInfo.name)
            .add(GalaxyInfoProperties.constellationKey, galaxyInfo.constellation)
            .add(GalaxyInfoProperties.distanceKey, galaxyInfo.distance)
            .add(GalaxyInfoProperties.availableGalaxiesKey, jsonBuilderFactory.createArrayBuilder().apply {
                galaxyInfo.availableGalaxies?.let { it ->
                    it.forEach { this.add(getJsonObjectBuilder(it)) }
                }
            })
}