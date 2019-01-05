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
import javax.json.JsonObject

class GalaxyInfoService : Service {

    companion object {
        val nameKey = "name"
        val constellationKey = "constellation"
        val distanceKey = "distance"
        val availableGalaxiesKey = "availableGalaxies"
    }

    private val galaxyInfoConfig = Config.create().get("galaxy-info")
    private val galaxyInfoJsonService = GalaxyInfoJsonService()

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
                .send<JsonObject>(galaxyInfoJsonService.getJsonObjectBuilder(this.get(projection)).build())
    }

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