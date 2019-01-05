package io.heterogeneousmicroservices.helidonseservice.service

import io.helidon.common.http.Http
import io.helidon.webserver.Handler
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import io.heterogeneousmicroservices.helidonseservice.config.GalaxyInfoProperties
import io.heterogeneousmicroservices.helidonseservice.model.GalaxyInfo
import io.heterogeneousmicroservices.helidonseservice.model.Projection
import javax.json.JsonObject

class GalaxyInfoService : Service {

    private val galaxyInfoProperties = GalaxyInfoProperties()
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
            galaxyInfoProperties.name,
            galaxyInfoProperties.constellation,
            galaxyInfoProperties.distance,
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