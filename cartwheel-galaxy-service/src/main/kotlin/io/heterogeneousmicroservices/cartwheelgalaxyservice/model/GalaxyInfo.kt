package io.heterogeneousmicroservices.cartwheelgalaxyservice.model

class GalaxyInfo(
        val name: String,
        // todo add yet another param(s)
        val availableGalaxies: List<GalaxyInfo>
)