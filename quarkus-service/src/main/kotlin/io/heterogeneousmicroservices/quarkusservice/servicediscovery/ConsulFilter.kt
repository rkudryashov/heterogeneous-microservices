package io.heterogeneousmicroservices.quarkusservice.servicediscovery

import org.apache.http.client.utils.URIBuilder
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.ext.Provider

@Provider
@ApplicationScoped
class ConsulFilter(
    @Inject private val consulClient: ConsulClient
) : ClientRequestFilter {

    override fun filter(requestContext: ClientRequestContext) {
        val serviceName = requestContext.uri.host
        val serviceInstance = consulClient.getServiceInstance(serviceName)
        val newUri = URIBuilder(requestContext.uri)
            .setHost(serviceInstance.address)
            .setPort(serviceInstance.port)
            .build()

        requestContext.uri = newUri
    }
}
