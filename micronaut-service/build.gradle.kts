import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val micronautVersion: String by project
val logbackVersion: String by project
val junitVersion: String by project

plugins {
    application
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
    kotlin("kapt")
    jacoco
}

application {
    mainClassName = "io.heterogeneousmicroservices.micronautservice.MicronautServiceApplication"
}

dependencies {
    kapt("io.micronaut:micronaut-inject-java:$micronautVersion")
    implementation(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation(kotlin("stdlib"))
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    runtimeOnly("io.micronaut:micronaut-discovery-client")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

tasks {
    withType<ShadowJar> {
        mergeServiceFiles()
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
