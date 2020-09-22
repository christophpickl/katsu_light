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
    mainClassName = "katsu.Katsu"
}

dependencies {
    implementation("org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonDate}")
    implementation("io.github.microutils:kotlin-logging:${Versions.klogging}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
//    implementation("joda-time:joda-time:${Versions.joda}")
    implementation("com.google.guava:guava:${Versions.guava}")

    testImplementation("org.testng:testng:${Versions.testng}")
//    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}")
//    testImplementation("io.mockk:mockk:${Versions.mockk}")
    // testImplementation("org.skyscreamer:jsonassert:${Versions.jsonassert}")
    // testImplementation("com.jayway.jsonpath:json-path:${Versions.jsonPath}")
    // testImplementation("com.github.tomakehurst:wiremock-jre8:${Versions.wiremock}")
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
