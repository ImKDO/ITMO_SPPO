plugins {
    id("java")
}

group = "project.lab5"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")

}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "project.lab5.Main"
    }
}


tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

