package io.heterogeneousmicroservices.universeservice.web

import io.heterogeneousmicroservices.universeservice.service.ApplicationInfoService
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class ApplicationInfoHandler(private val applicationInfoService: ApplicationInfoService) {

    fun getInfo(request: ServerRequest) = ServerResponse.ok()
            .contentType(APPLICATION_JSON_UTF8)
            .body(fromObject(applicationInfoService.get()))
}