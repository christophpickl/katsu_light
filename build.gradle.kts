import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

plugins {
    kotlin("jvm") version "1.4.10"
    application
}


application {
    mainClassName = "asdf"
}

dependencies {
    testImplementation("org.testng:testng:${Versions.testng}")
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
