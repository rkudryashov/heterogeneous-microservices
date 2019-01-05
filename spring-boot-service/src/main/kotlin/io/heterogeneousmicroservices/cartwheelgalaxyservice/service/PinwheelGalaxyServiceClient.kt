package io.heterogeneousmicroservices.cartwheelgalaxyservice.service

import io.heterogeneousmicroservices.cartwheelgalaxyservice.model.GalaxyInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

// todo call by id
@FeignClient(name = "pinwheel-galaxy-service", url = "http://localhost:8081")
interface PinwheelGalaxyServiceClient {

    @GetMapping("/galaxy-info")
    fun getGalaxyInfo(): GalaxyInfo
}