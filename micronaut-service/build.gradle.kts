import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val micronautVersion: String by project
val jacksonModuleKotlinVersion: String by project
val logbackVersion: String by project
val junitVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
    id("io.spring.dependency-management")
    id("com.github.johnrengelman.shadow")
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    implementation(kotlin("stdlib-jdk8"))
//    implementation(kotlin("reflect"))
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    kaptTest("io.micronaut:micronaut-inject-java")
    runtime("ch.qos.logback:logback-classic:$logbackVersion")
    runtime("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonModuleKotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:$micronautVersion")
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
