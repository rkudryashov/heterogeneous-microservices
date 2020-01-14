package io.heterogeneousmicroservices.quarkusservice.web

import io.heterogeneousmicroservices.quarkusservice.service.ApplicationInfoService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/application-info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ApplicationInfoResource(
    @Inject private val applicationInfoService: ApplicationInfoService
) {

    @GET
    fun get(@QueryParam("request-to") requestTo: String?): Response =
        Response.ok(applicationInfoService.get(requestTo)).build()

    @GET
    @Path("/logo")
    @Produces("image/png")
    fun logo(): Response = Response.ok(applicationInfoService.getLogo()).build()
}