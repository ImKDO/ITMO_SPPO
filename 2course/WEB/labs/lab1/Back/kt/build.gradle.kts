plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "web.kt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files("libs/fastcgi-lib.jar"))
}



tasks.test {
    useJUnitPlatform()

}
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "MainKt" // Укажите ваш главный класс
        )
    }
}
kotlin {
    jvmToolchain(17)
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("lab1.jar")
    configurations = listOf(project.configurations.runtimeClasspath.get())
}


////


////