package io.heterogeneousmicroservices.helidonservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.orbitz.consul.Consul
import com.orbitz.consul.model.health.Service
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class ServiceClient(
    private val consulClient: Consul
) {

    private val httpClient = HttpClient.newHttpClient()
    private val objectMapper = jacksonObjectMapper()
    private var serviceInstanceIndex = 0

    fun getApplicationInfo(serviceName: String): ApplicationInfo {
        val serviceInstance = getNext(serviceName)
        val serviceUrl = "http://${serviceInstance.address}:${serviceInstance.port}"
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$serviceUrl/application-info"))
            .build()
        val response = httpClient.send(request, BodyHandlers.ofString())
        return objectMapper.readValue(response.body())
    }

    private fun getNext(serviceName: String): Service {
        val serviceInstances = consulClient.healthClient().getHealthyServiceInstances(serviceName).response
        val selectedInstance = serviceInstances[serviceInstanceIndex]
        serviceInstanceIndex = (serviceInstanceIndex + 1) % serviceInstances.size
        return selectedInstance.service
    }
}