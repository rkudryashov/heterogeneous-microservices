package io.heterogeneousmicroservices.paralleluniverseservice.model

class ApplicationInfo(
        val name: String,
        val neigborhoods: List<ApplicationInfo>?
)