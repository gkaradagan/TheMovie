import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED

// Kotlin DSL syntax for project level build.gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradleOldWayPlugins.ANDROID_GRADLE)
        classpath(GradleOldWayPlugins.KOTLIN_GRADLE)
        classpath(GradleOldWayPlugins.HILT)
    }
}
plugins {
    detekt
    id(GradlePluginId.GIT_HOOKS)
    id(GradlePluginId.KTLINT)
    id(GradlePluginId.UPDATE_DEPENDENCIES)
    id(GradlePluginId.JACOCO)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    plugins.apply(GradlePluginId.SPOTLESS)
    plugins.apply(GradlePluginId.KTLINT)

    tasks {
        withType<Test> {
            testLogging {
                // set options for log level LIFECYCLE
                events = setOf(
                    FAILED,
                    STARTED,
                    PASSED,
                    SKIPPED,
                    STANDARD_OUT
                )
                exceptionFormat = FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }

            maxParallelForks =
                (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        }
    }
}

// JVM target applied to all Kotlin tasks across all sub-projects
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
