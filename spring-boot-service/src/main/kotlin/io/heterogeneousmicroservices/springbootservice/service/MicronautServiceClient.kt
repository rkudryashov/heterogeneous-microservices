package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

// todo call by id
@FeignClient(name = "micronaut-service", url = "http://localhost:8084")
interface MicronautServiceClient {

    @GetMapping("/application-info")
    fun getApplicationInfo(): ApplicationInfo
}