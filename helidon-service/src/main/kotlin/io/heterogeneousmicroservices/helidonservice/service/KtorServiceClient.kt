package io.heterogeneousmicroservices.helidonservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.orbitz.consul.Consul
import com.orbitz.consul.model.health.ServiceHealth
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class KtorServiceClient(
    private val consulClient: Consul
) {

    private val httpClient = HttpClient.newHttpClient()
    private val objectMapper = jacksonObjectMapper()
    private val ktorServiceName = "ktor-service"
    private var serviceInstanceIndex = 0

    fun getApplicationInfo(): ApplicationInfo {
        val serviceInstance = getNext()
        val serviceUrl = "http://${serviceInstance.service.address}:${serviceInstance.service.port}"
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$serviceUrl/application-info"))
            .build()
        val response = httpClient.send(request, BodyHandlers.ofString())
        return objectMapper.readValue(response.body())
    }

    private fun getNext(): ServiceHealth {
        val serviceInstances = consulClient.healthClient().getHealthyServiceInstances(ktorServiceName).response
        val selectedInstance = serviceInstances[serviceInstanceIndex]
        serviceInstanceIndex = (serviceInstanceIndex + 1) % serviceInstances.size
        return selectedInstance
    }
}