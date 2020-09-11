

repositories {
    mavenLocal()
}

plugins {
    kotlin("jvm") version aloha.Versions.kotlin
    application
}


application {
    mainClassName = "asdf"
}

dependencies {
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.jvm
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    withType<Jar> {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
        from(configurations["compile"].map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<Test> {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
//            showStandardStreams = true
        }
        useTestNG {
        }
    }
}
