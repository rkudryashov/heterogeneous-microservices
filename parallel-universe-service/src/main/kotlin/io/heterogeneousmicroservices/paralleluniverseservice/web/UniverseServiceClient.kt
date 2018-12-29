package io.heterogeneousmicroservices.paralleluniverseservice.web

import io.heterogeneousmicroservices.paralleluniverseservice.model.ApplicationInfo
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

// todo rename after module rename
// todo call by id
@Client("http://localhost:8080")
interface UniverseServiceClient {

    @Get("/application-info")
    fun getApplicationInfo(): ApplicationInfo
}