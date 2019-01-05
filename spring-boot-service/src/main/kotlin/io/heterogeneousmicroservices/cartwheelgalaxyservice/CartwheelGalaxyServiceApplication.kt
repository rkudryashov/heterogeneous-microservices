package io.heterogeneousmicroservices.cartwheelgalaxyservice

import io.heterogeneousmicroservices.cartwheelgalaxyservice.service.PinwheelGalaxyServiceClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(clients = [PinwheelGalaxyServiceClient::class])
class CartwheelGalaxyServiceApplication

fun main(args: Array<String>) {
    runApplication<CartwheelGalaxyServiceApplication>(*args)
}
