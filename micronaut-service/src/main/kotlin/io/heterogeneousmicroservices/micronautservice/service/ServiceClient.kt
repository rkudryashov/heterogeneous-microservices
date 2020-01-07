package io.heterogeneousmicroservices.micronautservice.service

import io.heterogeneousmicroservices.micronautservice.model.ApplicationInfo
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceClient(
    @Client(helidonServiceName) @Inject private val helidonServiceClient: RxHttpClient,
    @Client(ktorServiceName) @Inject private val ktorServiceClient: RxHttpClient,
    @Client(micronautServiceName) @Inject private val micronautServiceClient: RxHttpClient,
    @Client(quarkusServiceName) @Inject private val quarkusServiceClient: RxHttpClient,
    @Client(springBootServiceName) @Inject private val springBootServiceClient: RxHttpClient
) {

    companion object {
        const val helidonServiceName = "helidon-service"
        const val ktorServiceName = "ktor-service"
        const val micronautServiceName = "micronaut-service"
        const val quarkusServiceName = "quarkus-service"
        const val springBootServiceName = "spring-boot-service"
    }

    private val serviceClients = mapOf(
        helidonServiceName to helidonServiceClient,
        ktorServiceName to ktorServiceClient,
        micronautServiceName to micronautServiceClient,
        quarkusServiceName to quarkusServiceClient,
        springBootServiceName to springBootServiceClient
    )

    fun getApplicationInfo(serviceName: String): ApplicationInfo {
        val httpClient =
            serviceClients[serviceName] ?: throw IllegalArgumentException("Incorrect service name: $serviceName")
        val request = HttpRequest.GET<ApplicationInfo>("/application-info")
        return httpClient.retrieve(request, ApplicationInfo::class.java).blockingFirst()
    }
}