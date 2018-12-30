package io.heterogeneousmicroservices.paralleluniverseservice

import io.micronaut.runtime.Micronaut

object ParallelUniverseServiceApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("io.heterogeneousmicroservices.paralleluniverseservice")
                .mainClass(ParallelUniverseServiceApplication.javaClass)
                .start()
    }
}