import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "me.durgaprasad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.1")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}