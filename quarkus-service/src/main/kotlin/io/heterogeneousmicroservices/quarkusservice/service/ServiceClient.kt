package io.heterogeneousmicroservices.quarkusservice.service

import io.heterogeneousmicroservices.quarkusservice.model.ApplicationInfo
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ServiceClient(
    @Inject @RestClient @field: RestClient private val helidonServiceClient: HelidonServiceClient,
    @Inject @RestClient @field: RestClient private val ktorServiceClient: KtorServiceClient,
    @Inject @RestClient @field: RestClient private val micronautServiceClient: MicronautServiceClient,
    @Inject @RestClient @field: RestClient private val quarkusServiceClient: QuarkusServiceClient,
    @Inject @RestClient @field: RestClient private val springBootServiceClient: SpringBootServiceClient
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
        val externalServiceClient =
            serviceClients[serviceName] ?: throw IllegalArgumentException("Incorrect service name: $serviceName")

        return externalServiceClient.getApplicationInfo()
    }
}


