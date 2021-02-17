package commons

import GradlePluginId
import LibraryDependency
import extension.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(GradlePluginId.ANDROID_LIBRARY)
        project.plugins.apply(GradlePluginId.KOTLIN_ANDROID)
        project.plugins.apply(GradlePluginId.KOTLIN_KAPT)
        project.configureAndroidLibrary()
        project.dependencies {
            implementation(LibraryDependency.KOTLIN_STDLIB)
        }
    }
}