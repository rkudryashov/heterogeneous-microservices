package io.heterogeneousmicroservices.pinwheelgalaxyservice.web

import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.Projection
import io.heterogeneousmicroservices.pinwheelgalaxyservice.service.GalaxyInfoService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller(
        value = "/galaxy-info",
        consumes = [MediaType.APPLICATION_JSON],
        produces = [MediaType.APPLICATION_JSON]
)
class GalaxyInfoController(
        private val galaxyInfoService: GalaxyInfoService
) {

    @Get
    fun index(projection: Projection?): GalaxyInfo = galaxyInfoService.get(projection ?: Projection.DEFAULT)
}