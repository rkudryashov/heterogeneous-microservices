package io.heterogeneousmicroservices.quarkusservice.servicediscovery

import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class ConsulRegistrationBean(
    @Inject private val consulClient: ConsulClient
) {

    fun onStart(@Observes event: StartupEvent) {
        consulClient.register()
    }
}