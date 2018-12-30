package io.heterogeneousmicroservices.universeservice.model

class ApplicationInfo(
        val name: String,
        // todo add yet another param(s)
        val neighborhoods: List<ApplicationInfo>
)