package io.heterogeneousmicroservices.springbootservice.web

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import io.heterogeneousmicroservices.springbootservice.service.ApplicationInfoService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["application-info"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class ApplicationInfoController(
    private val applicationInfoService: ApplicationInfoService
) {

    @GetMapping
    fun get(requestTo: String?): ApplicationInfo = applicationInfoService.get(requestTo)

    @GetMapping(path = ["/logo"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getLogo(): ByteArray = applicationInfoService.getLogo()
}