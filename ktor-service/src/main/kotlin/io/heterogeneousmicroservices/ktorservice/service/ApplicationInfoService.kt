package io.heterogeneousmicroservices.ktorservice.service

import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.model.ApplicationInfo
import io.heterogeneousmicroservices.ktorservice.model.Projection
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

class ApplicationInfoService(
    private val applicationInfoProperties: ApplicationInfoProperties
) {

    private val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

    fun get(projection: Projection) = ApplicationInfo(
        applicationInfoProperties.name,
        ApplicationInfo.Framework(
            applicationInfoProperties.frameworkName,
            applicationInfoProperties.frameworkReleaseYear
        ),
        when (projection) {
            Projection.DEFAULT -> null
            Projection.FULL ->
                runBlocking {
                    getFollowingApplicationInfo()
                }
        }
    )

    private suspend fun getFollowingApplicationInfo(): ApplicationInfo {
        return httpClient.get("http://localhost:8083/application-info") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}