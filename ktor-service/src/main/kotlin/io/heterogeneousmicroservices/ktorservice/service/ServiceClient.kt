package io.heterogeneousmicroservices.ktorservice.service

import com.orbitz.consul.Consul
import io.heterogeneousmicroservices.ktorservice.feature.ConsulFeature
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class ServiceClient(
    private val consulClient: Consul
) {

    private val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
        install(ConsulFeature) {
            this.consulClient = this@ServiceClient.consulClient
        }
    }

    fun getApplicationInfo(serviceName: String): ApplicationInfo = runBlocking {
        httpClient.get<ApplicationInfo>("http://$serviceName/application-info") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}