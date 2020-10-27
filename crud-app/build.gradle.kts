plugins {
    kotlin("jvm") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    jcenter()
    maven(url = "https://jitpack.io")
}


dependencies {

    val exposedVersion = "0.26.1"
    val h2Version = "1.4.200"
    val ktorVersion = "1.4.1"
    val junitVersion = "4.4"

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:-SNAPSHOT")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("junit:junit:$junitVersion")
}




