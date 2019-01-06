package io.heterogeneousmicroservices.micronautservice.web

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.heterogeneousmicroservices.micronautservice.model.Projection
import io.heterogeneousmicroservices.micronautservice.service.ApplicationInfoService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller(
        value = "/application-info",
        consumes = [MediaType.APPLICATION_JSON],
        produces = [MediaType.APPLICATION_JSON]
)
class ApplicationInfoController(
        private val applicationInfoService: ApplicationInfoService
) {

    @Get
    fun get(projection: Projection?): ApplicationInfo = applicationInfoService.get(projection ?: Projection.DEFAULT)
}