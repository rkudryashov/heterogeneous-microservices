package io.heterogeneousmicroservices.helidonseservice.model

data class ApplicationInfo(
        val name: String,
        val framework: Framework,
        // todo image
        val followingApplication: ApplicationInfo?) {

    class Framework(
            val name: String,
            val releaseYear: Int
    )
}