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
import ModuleDependency
import addNavigationComponent

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(GradlePluginId.ANDROID_LIBRARY)
        project.plugins.apply(GradlePluginId.KOTLIN_ANDROID)
        project.plugins.apply(GradlePluginId.KOTLIN_KAPT)
        project.plugins.apply(GradlePluginId.HILT)
        project.configureAndroidLibrary()
        project.dependencies {
            implementation(project(ModuleDependency.CORE))
            implementation(project(ModuleDependency.NAVIGATION))

            implementation(LibraryDependency.KOTLIN_STDLIB)
            implementation(LibraryDependency.ANDROIDX_CORE_KTX)
            implementation(LibraryDependency.ANDROIDX_APPCOMPAT)
            implementation(LibraryDependency.MATERIAL)

            implementation(LibraryDependency.TIMBER)
            implementation(LibraryDependency.COROUTINES)

            implementation(LibraryDependency.ANDROIDX_CONSTRAINTLAYOUT)
            implementation(LibraryDependency.ANDROIDX_RECYCLERVIEW)
            implementation(LibraryDependency.ANDROIDX_VIEWPAGER2)
            implementation(LibraryDependency.ANDROIDX_FRAGMENT_KTX)

            addLifecycleDependencies()

            addNavigationComponent()

            addHilt()

            addTestDependencies()
        }
    }
}