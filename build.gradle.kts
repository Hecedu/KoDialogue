plugins {
    java
    kotlin("jvm") version "1.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.testng:testng:6.14.3")
    testImplementation("junit", "junit", "4.12")
    testImplementation(kotlin("test-js"))
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.4.10")
    testImplementation(kotlin("test"))
}

