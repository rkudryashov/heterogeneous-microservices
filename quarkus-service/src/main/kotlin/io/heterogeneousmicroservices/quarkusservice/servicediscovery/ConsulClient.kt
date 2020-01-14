package io.heterogeneousmicroservices.quarkusservice.servicediscovery

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.health.Service
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ConsulClient(
    @ConfigProperty(name = "application-info.name")
    private val serviceName: String,
    @ConfigProperty(name = "quarkus.http.port")
    private val port: Int
) {

    private val consulUrl = "http://localhost:8500"
    private val consulClient by lazy {
        Consul.builder().withUrl(consulUrl).build()
    }
    private var serviceInstanceIndex: Int = 0

    fun register() {
        consulClient.agentClient().register(createConsulRegistration())
    }

    fun getServiceInstance(serviceName: String): Service {
        val serviceInstances = consulClient.healthClient().getHealthyServiceInstances(serviceName).response
        val selectedInstance = serviceInstances[serviceInstanceIndex]
        serviceInstanceIndex = (serviceInstanceIndex + 1) % serviceInstances.size
        return selectedInstance.service
    }

    private fun createConsulRegistration() = ImmutableRegistration.builder()
        .id("$serviceName-$port")
        .name(serviceName)
        .address("localhost")
        .port(port)
        .build()
}