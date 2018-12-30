package io.heterogeneousmicroservices.cartwheelgalaxyservice.model

data class GalaxyInfo(
        val name: String,
        val constellation: String,
        val distance: Double,
        // todo image
        val availableGalaxies: List<GalaxyInfo>?
)