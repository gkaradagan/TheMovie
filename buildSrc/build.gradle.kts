plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://plugins.gradle.org/m2/")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object PluginVersions {
    // When you change it, you should change GRADLE_VERSION on Versions
    const val GRADLE_VERSION = "4.1.2"

    // When you change it, you should change KOTLIN_VERSION on Versions
    const val KOTLIN_VERSION = "1.4.20"

    const val SPOTLESS = "5.8.2"
    const val DETEKT = "1.15.0-RC1"
    const val KTLINT = "9.4.1"
    const val JACOCO = "0.16.0"
    const val BEN_MANES = "0.36.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginVersions.GRADLE_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginVersions.KOTLIN_VERSION}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${PluginVersions.KTLINT}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginVersions.DETEKT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginVersions.SPOTLESS}")
    implementation("com.vanniktech:gradle-android-junit-jacoco-plugin:${PluginVersions.JACOCO}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginVersions.BEN_MANES}")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}

gradlePlugin {
    plugins {
        create("androidLibraryPlugin") {
            id = "common-android-library"
            implementationClass = "commons.AndroidLibraryPlugin"
        }
        create("AndroidFeaturePlugin") {
            id = "common-feature-library"
            implementationClass = "commons.AndroidFeaturePlugin"
        }
    }
}