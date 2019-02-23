package io.heterogeneousmicroservices.helidonservice.service

import io.helidon.common.configurable.Resource
import io.helidon.common.http.Http
import io.helidon.common.http.MediaType
import io.helidon.webserver.Handler
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import io.heterogeneousmicroservices.helidonservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import io.heterogeneousmicroservices.helidonservice.model.Projection
import javax.json.JsonObject

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties,
    private val applicationInfoJsonService: ApplicationInfoJsonService,
    private val ktorServiceClient: KtorServiceClient
) : Service {

    override fun update(rules: Routing.Rules) {
        rules.get("/", Handler { request, response -> this.getApplicationInfoJsonObject(request, response) })
        rules.get("/logo", Handler { request, response -> this.getLogo(request, response) })
    }

    private fun getApplicationInfoJsonObject(request: ServerRequest, response: ServerResponse) {
        val projection = request.queryParams()
            .first("projection")
            .map { Projection.valueOf(it.toUpperCase()) }
            .orElse(Projection.DEFAULT)

        response
            .status(Http.ResponseStatus.create(200))
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
            Projection.FULL -> ktorServiceClient.getApplicationInfo()
        }
    )

    private fun getLogo(request: ServerRequest, response: ServerResponse) {
        val logo = Resource.create("logo.png").bytes()
        response.headers().contentType(MediaType.create("image", "png"))
        response
            .status(Http.ResponseStatus.create(200))
            .send<ByteArray>(logo)
    }
}