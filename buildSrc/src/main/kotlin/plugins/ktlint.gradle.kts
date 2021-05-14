package plugins

import Versions
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

apply<KtlintPlugin>()

configure<KtlintExtension> {
    version.set(Versions.KTLINT_INTERNAL)
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    additionalEditorconfigFile.set(file("${project.rootDir}/tools/.editorconfig"))
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }

    //Workaround - https://github.com/JLLeitschuh/ktlint-gradle/issues/458
    configurations.named("ktlint").configure {
        resolutionStrategy {
            dependencySubstitution {
                substitute(module("com.pinterest:ktlint")).with(variant(module("com.pinterest:ktlint:${Versions.KTLINT_INTERNAL}")) {
                    attributes {
                        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling::class, Bundling.EXTERNAL))
                    }
                })
            }
        }
    }
}
