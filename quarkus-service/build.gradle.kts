import io.quarkus.gradle.tasks.QuarkusBuild
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "1.0.0"

val quarkusVersion: String by project
val consulClientVersion: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.quarkus")
    jacoco
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-config-yaml")
    // todo use native service Service Discovery support (https://github.com/quarkusio/quarkus/issues/5812)
//    implementation("org.microprofile-ext.config-ext:configsource-consul:1.0.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.orbitz.consul:consul-client:$consulClientVersion")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

allOpen {
    annotation("javax.enterprise.context.ApplicationScoped")
}

tasks {
    withType<QuarkusBuild> {
        isUberJar = true
    }
    withType<Test> {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
