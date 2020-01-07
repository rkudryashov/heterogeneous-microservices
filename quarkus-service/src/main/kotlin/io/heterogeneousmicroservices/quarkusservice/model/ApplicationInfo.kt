package io.heterogeneousmicroservices.quarkusservice.model

data class ApplicationInfo(
    val name: String,
    val framework: Framework,
    val requestedService: ApplicationInfo?
) {

    data class Framework(
        val name: String,
        val releaseYear: Int
    )
}