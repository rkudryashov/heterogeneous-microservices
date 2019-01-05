package io.heterogeneousmicroservices.pinwheelgalaxyservice.service

import io.heterogeneousmicroservices.pinwheelgalaxyservice.model.GalaxyInfo
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

// todo call by id
@Client("http://localhost:8080")
interface CartwheelGalaxyServiceClient {

    @Get("/galaxy-info")
    fun getGalaxyInfo(): GalaxyInfo
}