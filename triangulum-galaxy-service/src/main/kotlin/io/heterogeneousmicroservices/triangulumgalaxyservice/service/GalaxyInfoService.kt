package io.heterogeneousmicroservices.triangulumgalaxyservice.service

import io.helidon.common.http.Http
import io.helidon.config.Config
import io.helidon.webserver.Handler
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import io.heterogeneousmicroservices.triangulumgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.triangulumgalaxyservice.model.Projection
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

class GalaxyInfoService : Service {

    private val galaxyInfoConfig = Config.create().get("galaxy-info")
    private val jsonBuilderFactory = Json.createBuilderFactory(null)
    private val nameKey = "name"
    private val constellationKey = "constellation"
    private val distanceKey = "distance"
    private val availableGalaxiesKey = "availableGalaxies"

    override fun update(rules: Routing.Rules<out Routing.Rules<*>>) {
        rules
                .get("/", Handler { request, response -> this.getGalaxyInfoJsonObject(request, response) })
    }

    private fun getGalaxyInfoJsonObject(request: ServerRequest, response: ServerResponse) {
        val projection = request.queryParams()
                .first("projection")
                .map { Projection.valueOf(it.toUpperCase()) }
                .orElse(Projection.DEFAULT)

        response
                .status(Http.ResponseStatus.from(200))
                .send<JsonObject>(getJsonObjectBuilderForGalaxyInfo(this.get(projection)).build())
    }

    private fun getJsonObjectBuilderForGalaxyInfo(galaxyInfo: GalaxyInfo): JsonObjectBuilder = jsonBuilderFactory.createObjectBuilder()
            .add(nameKey, galaxyInfo.name)
            .add(constellationKey, galaxyInfo.constellation)
            .add(distanceKey, galaxyInfo.distance)
            .add(availableGalaxiesKey, jsonBuilderFactory.createArrayBuilder().apply {
                galaxyInfo.availableGalaxies?.let { it ->
                    it.forEach { this.add(getJsonObjectBuilderForGalaxyInfo(it)) }
                }
            })

    private fun get(projection: Projection): GalaxyInfo = GalaxyInfo(
            galaxyInfoConfig[nameKey].asString(),
            galaxyInfoConfig[constellationKey].asString(),
            galaxyInfoConfig[distanceKey].asDouble(),
            when (projection) {
                Projection.DEFAULT -> null
                Projection.FULL -> getAvailableGalaxies()
            }
    )

    private fun getAvailableGalaxies(): List<GalaxyInfo> {
        // todo implement
        return listOf<GalaxyInfo>()
    }
}