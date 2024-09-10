
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"

    id ("com.gradleup.shadow") version "8.3.0"

    kotlin("plugin.serialization") version "2.0.20"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.auth.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks{

    shadowJar{

    }


}

repositories {
    mavenCentral()
}

dependencies {

    val ktor_version = "2.2.4"

    // KMongo dependency
    implementation("org.litote.kmongo:kmongo:4.8.0")

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    implementation("commons-codec:commons-codec:1.17.1")

    // .env
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")



    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")


    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")


    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
