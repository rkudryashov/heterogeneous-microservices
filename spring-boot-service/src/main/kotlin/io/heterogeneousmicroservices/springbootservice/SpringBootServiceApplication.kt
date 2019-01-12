package io.heterogeneousmicroservices.springbootservice

import io.heterogeneousmicroservices.springbootservice.service.HelidonServiceClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(clients = [HelidonServiceClient::class])
class SpringBootServiceApplication

fun main(args: Array<String>) {
    runApplication<SpringBootServiceApplication>(*args)
}
