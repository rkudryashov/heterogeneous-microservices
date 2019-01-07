package io.heterogeneousmicroservices.micronautservice

import io.micronaut.runtime.Micronaut

object MicronautServiceApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("io.heterogeneousmicroservices.micronautservice")
            .mainClass(MicronautServiceApplication.javaClass)
            .start()
    }
}