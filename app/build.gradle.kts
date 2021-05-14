/*
 * Copyright 2020 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import commons.implementation
import commons.project
import extension.setDefaults

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }
val apiKey: String = gradleLocalProperties(rootDir).getProperty("api.key")
val apiUrl: String = project.property("api.url") as String

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.getSemanticAppVersionName()
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        buildConfigField("String", "API_KEY", apiKey)
        buildConfigField("String", "API_URL", apiUrl)
    }

    buildTypes {

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }

        testOptions {
            unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
        }

        compileOptions {
            sourceCompatibility(javaVersion)
            targetCompatibility(javaVersion)
        }
    }

    buildFeatures {
        viewBinding = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

    compileOptions {
        sourceCompatibility(javaVersion)
        targetCompatibility(javaVersion)
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    lintOptions.setDefaults(file("${project.rootDir}/tools/lint-rules.xml"))
}

dependencies {
    implementation(project(ModuleDependency.CORE))
    implementation(project(ModuleDependency.NAVIGATION))

    implementation(project(ModuleDependency.FEATURE_MOVIE))
    implementation(project(ModuleDependency.FEATURE_SERIES))
    implementation(project(ModuleDependency.FEATURE_FAVOURITE))

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

    debugImplementation(LibraryDependency.LEAK_CANARY)

    addLifecycleDependencies()

    addNavigationComponent()

    addHilt()

    addTestDependencies()
}
