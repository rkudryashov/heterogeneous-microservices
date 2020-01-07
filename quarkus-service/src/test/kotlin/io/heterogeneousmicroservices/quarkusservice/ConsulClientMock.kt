package io.heterogeneousmicroservices.quarkusservice

import io.heterogeneousmicroservices.quarkusservice.servicediscovery.ConsulClient
import io.quarkus.test.Mock
import javax.enterprise.context.ApplicationScoped

@Mock
@ApplicationScoped
class ConsulClientMock : ConsulClient("", 0) {

    // do nothing
    override fun register() {
    }
}