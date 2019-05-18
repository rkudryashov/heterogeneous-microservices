package io.heterogeneousmicroservices.micronautservice.web

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
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
    fun get(requestTo: String?): ApplicationInfo = applicationInfoService.get(requestTo)

    @Get("/logo", produces = [MediaType.IMAGE_PNG])
    fun getLogo(): ByteArray = applicationInfoService.getLogo()
}