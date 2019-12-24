package io.heterogeneousmicroservices.ktorservice.feature

import com.orbitz.consul.Consul
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey

class ConsulFeature(private val consulClient: Consul) {

    class Config {
        lateinit var consulClient: Consul
    }

    companion object Feature : HttpClientFeature<Config, ConsulFeature> {
        var serviceInstanceIndex: Int = 0

        override val key = AttributeKey<ConsulFeature>("ConsulFeature")

        override fun prepare(block: Config.() -> Unit) = ConsulFeature(Config().apply(block).consulClient)

        override fun install(feature: ConsulFeature, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
                val serviceName = context.url.host
                val serviceInstances =
                    feature.consulClient.healthClient().getHealthyServiceInstances(serviceName).response
                val selectedInstance = serviceInstances[serviceInstanceIndex]
                context.url.apply {
                    host = selectedInstance.service.address
                    port = selectedInstance.service.port
                }
                serviceInstanceIndex = (serviceInstanceIndex + 1) % serviceInstances.size
            }
        }
    }
}