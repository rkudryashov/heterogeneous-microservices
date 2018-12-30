package io.heterogeneousmicroservices.cartwheelgalaxyservice.web

import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.Projection
import io.heterogeneousmicroservices.cartwheelgalaxyservice.service.GalaxyInfoService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
        path = ["/galaxy-info"],
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
)
class GalaxyInfoController(
        private val galaxyInfoService: GalaxyInfoService
) {

    @GetMapping
    fun index(projection: Projection?): GalaxyInfo = galaxyInfoService.get(projection ?: Projection.DEFAULT)
}