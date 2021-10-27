import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"

    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

repositories {
    mavenCentral()
    mavenLocal()
}

version = "1.2.1.271021"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.ws:spring-ws:3.0.9.RELEASE")
    implementation("org.springframework.ws:spring-ws-core:3.0.9.RELEASE")

    implementation("org.springframework.cloud:spring-cloud-stream:3.0.9.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit:3.0.9.RELEASE")

    implementation("org.springframework.boot:spring-boot-starter-web")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
