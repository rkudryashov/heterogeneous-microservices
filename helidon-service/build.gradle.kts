import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val helidonVersion: String by project
val koinVersion: String by project
val consulClientVersion: String by project
val jacksonVersion: String by project
val logbackVersion: String by project
val junitVersion: String by project
val mockitoVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    application
    id("com.github.johnrengelman.shadow")
    kotlin("jvm")
    id("io.spring.dependency-management")
    jacoco
}

application {
    mainClassName = "io.heterogeneousmicroservices.helidonservice.HelidonServiceApplication"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.helidon.bundles:helidon-bundles-webserver")
    implementation("io.helidon.media.jackson:helidon-media-jackson-server")
    implementation("io.helidon.bundles:helidon-bundles-config")
    implementation("org.koin:koin-core:$koinVersion")
    implementation("com.orbitz.consul:consul-client:$consulClientVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    runtime("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.koin:koin-test:$koinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    // fixme temporary override mockito version that comes from koin-test
    testRuntime("org.mockito:mockito-core:$mockitoVersion")
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
            jvmTarget = "12"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
