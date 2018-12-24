package io.heterogeneousmicroservices.universeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UniverseServiceApplication

fun main(args: Array<String>) {
    runApplication<UniverseServiceApplication>(*args)
}
