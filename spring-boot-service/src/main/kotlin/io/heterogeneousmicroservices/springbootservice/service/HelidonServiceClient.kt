package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "helidon-service")
interface HelidonServiceClient {

    @GetMapping("/application-info")
    fun getApplicationInfo(): ApplicationInfo
}