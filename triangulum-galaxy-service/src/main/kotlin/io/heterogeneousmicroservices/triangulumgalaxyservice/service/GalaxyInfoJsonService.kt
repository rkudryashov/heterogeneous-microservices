package io.heterogeneousmicroservices.triangulumgalaxyservice.service

import io.heterogeneousmicroservices.triangulumgalaxyservice.model.GalaxyInfo
import javax.json.Json
import javax.json.JsonObjectBuilder

class GalaxyInfoJsonService {

    private val jsonBuilderFactory = Json.createBuilderFactory(null)

    fun getJsonObjectBuilder(galaxyInfo: GalaxyInfo): JsonObjectBuilder = jsonBuilderFactory.createObjectBuilder()
            .add(GalaxyInfoService.nameKey, galaxyInfo.name)
            .add(GalaxyInfoService.constellationKey, galaxyInfo.constellation)
            .add(GalaxyInfoService.distanceKey, galaxyInfo.distance)
            .add(GalaxyInfoService.availableGalaxiesKey, jsonBuilderFactory.createArrayBuilder().apply {
                galaxyInfo.availableGalaxies?.let { it ->
                    it.forEach { this.add(getJsonObjectBuilder(it)) }
                }
            })
}