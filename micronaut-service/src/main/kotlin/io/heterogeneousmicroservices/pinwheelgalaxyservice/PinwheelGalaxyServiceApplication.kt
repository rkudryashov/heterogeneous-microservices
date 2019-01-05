package io.heterogeneousmicroservices.pinwheelgalaxyservice

import io.micronaut.runtime.Micronaut

object PinwheelGalaxyServiceApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("io.heterogeneousmicroservices.pinwheelgalaxyservice")
                .mainClass(PinwheelGalaxyServiceApplication.javaClass)
                .start()
    }
}