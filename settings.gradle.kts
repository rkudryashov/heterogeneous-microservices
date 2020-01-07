rootProject.name = "heterogeneous-microservices"

include(
    "helidon-service",
    "ktor-service",
    "micronaut-service",
    "quarkus-service",
    "spring-boot-service"
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val shadowPluginVersion: String by settings
    val quarkusVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.allopen" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "com.github.johnrengelman.shadow" -> useVersion(shadowPluginVersion)
                "io.quarkus" -> useVersion(quarkusVersion)
            }
        }
    }
}