package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client(id = "spring-boot-service")
interface SpringBootServiceClient {

    @Get("/application-info")
    fun getApplicationInfo(): ApplicationInfo
}