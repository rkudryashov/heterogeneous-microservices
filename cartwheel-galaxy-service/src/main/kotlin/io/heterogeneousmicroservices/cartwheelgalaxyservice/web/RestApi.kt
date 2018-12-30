package io.heterogeneousmicroservices.cartwheelgalaxyservice.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class RestApi {

    @Bean
    fun itemsRouter(handler: GalaxyInfoHandler) = router {
        path("/galaxy-info").nest {
            GET("/", handler::getInfo)
        }
    }
}