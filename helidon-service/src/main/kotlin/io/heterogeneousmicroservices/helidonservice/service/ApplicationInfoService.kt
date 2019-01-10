package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.common.http.Http
import io.helidon.webserver.Handler
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import io.heterogeneousmicroservices.helidonservice.model.Projection
import javax.json.JsonObject

class ApplicationInfoService : Service {

    val applicationInfoProperties = ApplicationInfoProperties()
    private val applicationInfoJsonService = ApplicationInfoJsonService()

    override fun update(rules: Routing.Rules<out Routing.Rules<*>>) {
        rules
            .get("/", Handler { request, response -> this.getApplicationInfoJsonObject(request, response) })
    }

    private fun getApplicationInfoJsonObject(request: ServerRequest, response: ServerResponse) {
        val projection = request.queryParams()
            .first("projection")
            .map { Projection.valueOf(it.toUpperCase()) }
            .orElse(Projection.DEFAULT)

        response
            .status(Http.ResponseStatus.from(200))
            .send<JsonObject>(applicationInfoJsonService.getJsonObjectBuilder(this.get(projection)).build())
    }

    private fun get(projection: Projection): ApplicationInfo = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseDate
        ),
        when (projection) {
            Projection.DEFAULT -> null
            // todo implement
            Projection.FULL -> null
        }
    )
}