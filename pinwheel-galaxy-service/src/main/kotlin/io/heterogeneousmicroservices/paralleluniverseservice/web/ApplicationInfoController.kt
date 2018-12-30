package io.heterogeneousmicroservices.paralleluniverseservice.web

import io.heterogeneousmicroservices.paralleluniverseservice.model.ApplicationInfo
import io.heterogeneousmicroservices.paralleluniverseservice.service.ApplicationInfoService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller("/application-info")
class ApplicationInfoController(
        @Inject private val applicationInfoService: ApplicationInfoService
) {

    @Get(processes = [MediaType.APPLICATION_JSON])
    fun index(): ApplicationInfo = applicationInfoService.get()
}