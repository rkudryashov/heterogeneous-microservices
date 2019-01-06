package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8080")
interface SpringBootServiceClient {

    @Get("/application-info")
    fun getApplicationInfo(): ApplicationInfo
}