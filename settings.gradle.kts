rootProject.name = "heterogeneous-microservices"

include(
    "helidon-service",
    "ktor-service",
    "micronaut-service",
    "quarkus-service",
    "spring-boot-service"
)

pluginManagement {
    plugins {
        fun String.getVersion() = extra["$this.version"].toString()
        fun PluginDependenciesSpec.resolve(id: String, versionKey: String = id) = id(id) version versionKey.getVersion()

        resolve("org.jetbrains.kotlin.jvm")
        resolve("org.jetbrains.kotlin.kapt", "org.jetbrains.kotlin.jvm")
        resolve("org.jetbrains.kotlin.plugin.spring", "org.jetbrains.kotlin.jvm")
        resolve("org.jetbrains.kotlin.plugin.allopen", "org.jetbrains.kotlin.jvm")
        resolve("com.github.johnrengelman.shadow")
        resolve("io.quarkus")
        resolve("org.springframework.boot")
    }
}
