import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val analysisDir = file(projectDir)
val baselineFile = file("${project.rootDir}/tools/detekt/baseline.xml")
val configFile = file("${project.rootDir}/tools/detekt/detekt-config.yml")
val statisticsConfigFile = file("${project.rootDir}/tools/detekt/statistics.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

subprojects {

    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = javaVersion.toString()
    }

    configure<DetektExtension> {
        toolVersion = Versions.DETEKT_INTERNAL
        baseline = baselineFile
        input = objects.fileCollection().from(
            DetektExtension.DEFAULT_SRC_DIR_JAVA,
            "src/test/java",
            DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
            "src/test/kotlin"
        )
        config = files(configFile)

        reports {
            xml {
                enabled = true
                destination = file("${project.buildDir}/reports/detekt/detekt-report.xml")
            }
            html {
                enabled = true
                destination = file("${project.buildDir}/reports/detekt/detekt-report.html")
            }
        }
    }

    dependencies {
        detekt("io.gitlab.arturbosch.detekt:detekt-cli:${Versions.DETEKT_INTERNAL}")
        detektPlugins(project(":custom-detekt-rules"))
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.DETEKT_INTERNAL}")
    }
}

val detektFormat by tasks.registering(Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs the whole project at once."
    parallel = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektProjectBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
}
