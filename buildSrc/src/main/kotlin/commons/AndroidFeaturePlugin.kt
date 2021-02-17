package commons

import GradlePluginId
import LibraryDependency
import extension.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import addLifecycleDependencies
import addHilt
import addTestDependencies
import org.gradle.kotlin.dsl.project

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(GradlePluginId.ANDROID_LIBRARY)
        project.plugins.apply(GradlePluginId.KOTLIN_ANDROID)
        project.plugins.apply(GradlePluginId.KOTLIN_KAPT)
        project.plugins.apply(GradlePluginId.HILT)
        project.configureAndroidLibrary()
        project.dependencies {
            implementation(project(":library:core"))

            implementation(LibraryDependency.KOTLIN_STDLIB)
            implementation(LibraryDependency.ANDROIDX_CORE_KTX)
            implementation(LibraryDependency.ANDROIDX_APPCOMPAT)
            implementation(LibraryDependency.MATERIAL)

            implementation(LibraryDependency.TIMBER)
            implementation(LibraryDependency.COROUTINES)

            addLifecycleDependencies()

            addHilt()

            addTestDependencies()
        }
    }
}