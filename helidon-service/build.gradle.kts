import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val helidonVersion: String by project
val kotlinCoroutinesVersion: String by project
val consulClientVersion: String by project
val logbackVersion: String by project
val junitVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    id("io.spring.dependency-management")
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("io.helidon.microprofile.bundles:helidon-microprofile-1.2:$helidonVersion")
    implementation("io.helidon.config:helidon-config-yaml")
    implementation("com.orbitz.consul:consul-client:$consulClientVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

dependencyManagement {
    imports {
        mavenBom("io.helidon:helidon-bom:$helidonVersion")
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
