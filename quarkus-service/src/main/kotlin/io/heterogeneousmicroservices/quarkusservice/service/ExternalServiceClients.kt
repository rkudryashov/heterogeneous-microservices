package io.heterogeneousmicroservices.quarkusservice.service

import io.heterogeneousmicroservices.quarkusservice.model.ApplicationInfo
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@ApplicationScoped
@Path("/")
interface ExternalServiceClient {
    @GET
    @Path("/application-info")
    @Produces("application/json")
    fun getApplicationInfo(): ApplicationInfo
}

@RegisterRestClient(baseUri = "http://helidon-service")
interface HelidonServiceClient : ExternalServiceClient

@RegisterRestClient(baseUri = "http://ktor-service")
interface KtorServiceClient : ExternalServiceClient

@RegisterRestClient(baseUri = "http://micronaut-service")
interface MicronautServiceClient : ExternalServiceClient

@RegisterRestClient(baseUri = "http://quarkus-service")
interface QuarkusServiceClient : ExternalServiceClient

@RegisterRestClient(baseUri = "http://spring-boot-service")
interface SpringBootServiceClient : ExternalServiceClient