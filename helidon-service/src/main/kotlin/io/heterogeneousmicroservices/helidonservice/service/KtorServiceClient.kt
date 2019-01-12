package io.heterogeneousmicroservices.helidonservice.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.heterogeneousmicroservices.helidonservice.model.ApplicationInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class KtorServiceClient {

    private val objectMapper = jacksonObjectMapper()

    fun getApplicationInfo(): ApplicationInfo {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8082/application-info"))
            .build()
        val response = client.send(request, BodyHandlers.ofString())
        return objectMapper.readValue(response.body())
    }
}