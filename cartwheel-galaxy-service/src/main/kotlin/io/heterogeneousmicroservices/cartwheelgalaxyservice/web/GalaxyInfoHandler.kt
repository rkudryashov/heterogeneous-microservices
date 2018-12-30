package io.heterogeneousmicroservices.cartwheelgalaxyservice.web

import io.heterogeneousmicroservices.cartwheelgalaxyservice.service.GalaxyInfoService
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class GalaxyInfoHandler(private val galaxyInfoService: GalaxyInfoService) {

    fun getInfo(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON_UTF8)
            .body(fromObject(galaxyInfoService.get()))
}