package io.heterogeneousmicroservices.triangulumgalaxyservice.config

import io.helidon.config.Config

class GalaxyInfoProperties {

    companion object {
        const val nameKey = "name"
        const val constellationKey = "constellation"
        const val distanceKey = "distance"
        const val availableGalaxiesKey = "availableGalaxies"
    }

    private val galaxyInfoConfig = Config.create().get("galaxy-info")

    val name = galaxyInfoConfig[nameKey].asString()
    val constellation = galaxyInfoConfig[constellationKey].asString()
    val distance = galaxyInfoConfig[distanceKey].asDouble()
}