package io.heterogeneousmicroservices.springbootservice.model

data class ApplicationInfo(
    val name: String,
    val framework: Framework,
    val followingApplication: ApplicationInfo?
) {

    data class Framework(
        val name: String,
        val releaseYear: Int
    )
}