object GradlePluginId {
    const val ANDROID_APPLICATION = "com.android.application"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val KOTLIN = "kotlin"
    const val HILT = "dagger.hilt.android.plugin"
    const val QUADRANT = "com.gaelmarhic.quadrant"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"

    const val COMMON_ANDROID_LIBRARY = "common-android-library"
    const val COMMON_FEATURE_LIBRARY = "common-feature-library"
    const val GIT_HOOKS = "plugins.git-hooks"
    const val SPOTLESS = "plugins.spotless"
    const val UPDATE_DEPENDENCIES = "plugins.update-dependencies"
    const val DETEKT = "plugins.detekt"
    const val JACOCO = "plugins.jacoco"
    const val KTLINT = "plugins.ktlint"
}

object GradleOldWayPlugins {
    const val ANDROID_GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE_VERSION}"
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
    const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
}
