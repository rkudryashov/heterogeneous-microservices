package io.heterogeneousmicroservices.pinwheelgalaxyservice.model

class GalaxyInfo(
        val name: String,
        val availableGalaxies: List<GalaxyInfo>?
)