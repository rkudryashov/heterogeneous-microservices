package io.heterogeneousmicroservices.springbootservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class SpringBootServiceApplication {
    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper() = jacksonObjectMapper()
}

fun main(args: Array<String>) {
    runApplication<SpringBootServiceApplication>(*args)
}
