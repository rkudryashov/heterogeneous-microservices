package io.heterogeneousmicroservices.universeservice.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class RestApi {

    @Bean
    fun itemsRouter(handler: ApplicationInfoHandler) = router {
        path("/application-info").nest {
            GET("/", handler::getInfo)
        }
    }
}