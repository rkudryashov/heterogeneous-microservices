package io.heterogeneousmicroservices.springbootservice

import io.heterogeneousmicroservices.springbootservice.service.MicronautServiceClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(clients = [MicronautServiceClient::class])
class SpringBootServiceApplication

fun main(args: Array<String>) {
    runApplication<SpringBootServiceApplication>(*args)
}
