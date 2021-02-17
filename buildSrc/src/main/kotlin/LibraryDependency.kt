import commons.implementation
import commons.androidTestImplementation
import commons.kapt
import commons.kaptTest
import commons.testImplementation
import commons.kaptAndroidTest
import org.gradle.api.artifacts.dsl.DependencyHandler

object LibraryDependency {
    //Kotlin
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"

    //Hilt
    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val KAPT_HILT = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"

    //Androidx
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX}"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"
    const val ANDROIDX_CONSTRAINTLAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINTLAYOUT}"
    const val ANDROIDX_RECYCLERVIEW =
        "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_RECYCLERVIEW}"
    const val ANDROIDX_VIEWPAGER2 =
        "androidx.viewpager2:viewpager2:${Versions.ANDROIDX_VIEWPAGER2}"

    //DataStore
    const val DATASTORE =
        "androidx.datastore:datastore:${Versions.DATASTORE}"

    //ViewModel
    const val ANDROIDX_VIEW_MODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"

    //Livedata
    const val ANDROIDX_LIVEDATA =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"

    // Saved state module for ViewModel
    const val ANDROIDX_SAVED_VIEW_MODEL =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"

    // Lifecycle runtime
    const val ANDROIDX_LIFE_CYCLE_RUNTIME =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"

    // alternately - if using Java8, use the following instead of lifecycle-compiler
    const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"

    //Room
    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val KAPT_ROOM = "androidx.room:room-compiler:${Versions.ROOM}"

    //Kotlin Extensions and Coroutines support for Room
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    //Material
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"

    //Detekt
    const val DETEKT_API = "io.gitlab.arturbosch.detekt:detekt-api:${Versions.DETEKT_INTERNAL}"
    const val DETEKT_TEST = "io.gitlab.arturbosch.detekt:detekt-test:${Versions.DETEKT_INTERNAL}"

    //Network
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"

    //Json
    const val MOSHI = "ccom.squareup.moshi:moshi:${Versions.MOSHI}"
    const val KAPT_MOSHI = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"

    //Coroutines
    const val COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    //Timber
    const val TIMBER =
        "com.jakewharton.timber:timber:${Versions.TIMBER}"

    //LeakCanary --debugImplementation
    const val LEAK_CANARY =
        "com.squareup.leakcanary:leakcanary-android:${Versions.LEAK_CANARY}"
}

object TestDependencies {
    // Core library
    const val ANDROIDX_TEST_CORE = "androidx.test:core:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_ARCH_CORE = "androidx.arch.core:core-testing:${Versions.ANDROIDX_ARCH_TEST}"

    //Room Test helpers
    const val ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"

    //Assertions
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
    const val ANDROIDX_TRUTH = "androidx.test.ext:truth:${Versions.ANDROIDX_TEST}"

    //Coroutines
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"

    // AndroidJUnitRunner and JUnit Rules
    const val ANDROIDX_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"

    //Espresso
    const val ANDROIDX_ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${Versions.ANDROIDX_ESPRESSO_CORE}"
    const val ANDROIDX_ESPRESSO_IDLING =
        "androidx.test.espresso:espresso-idling-resource:${Versions.ANDROIDX_ESPRESSO_CORE}"
    const val ANDROIDX_ESPRESSO_INTENTANDROIDX_ESPRESSO_INTENT =
        "androidx.test.espresso:espresso-intents:${Versions.ANDROIDX_ESPRESSO_CORE}"

    //OkHttp Mock WebServer
    const val OKHTTP_MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}"

    //Robolectric
    const val ROBOLECTRIC = "org.robolectric:robolectric:${Versions.ROBOLECTRIC}"

    //Mockk
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"

    //Hilt For Robolectric tests.
    const val HILT = "com.google.dagger:hilt-android-testing:${Versions.HILT}"

    //Hilt with Kotlin.
    const val KAPT_TEST_HILT = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

fun DependencyHandler.addTestDependencies() {
    testImplementation(TestDependencies.ANDROIDX_TEST_CORE)
    testImplementation(TestDependencies.ANDROIDX_ARCH_CORE)
    testImplementation(TestDependencies.ANDROIDX_JUNIT)
    testImplementation(TestDependencies.ANDROIDX_TRUTH)
    testImplementation(TestDependencies.MOCKK)
    testImplementation(TestDependencies.COROUTINES_TEST)
    testImplementation(TestDependencies.OKHTTP_MOCK_WEB_SERVER)
    testImplementation(TestDependencies.ROBOLECTRIC)
    testImplementation(TestDependencies.HILT)
    kaptTest(TestDependencies.KAPT_TEST_HILT)
}

fun DependencyHandler.addAndroidTestDependencies() {
    androidTestImplementation(TestDependencies.ANDROIDX_RUNNER)
    androidTestImplementation(TestDependencies.ANDROIDX_RULES)
    androidTestImplementation(TestDependencies.ANDROIDX_TEST_CORE)
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_TRUTH)
    androidTestImplementation(TestDependencies.MOCKK)
    androidTestImplementation(TestDependencies.OKHTTP_MOCK_WEB_SERVER)
    androidTestImplementation(TestDependencies.ROOM_TESTING)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_IDLING)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_INTENTANDROIDX_ESPRESSO_INTENT)
    androidTestImplementation(TestDependencies.HILT)
    kaptAndroidTest(TestDependencies.KAPT_TEST_HILT)
}

fun DependencyHandler.addHilt() {
    implementation(LibraryDependency.HILT)
    kapt(LibraryDependency.KAPT_HILT)
}

fun DependencyHandler.addRoomDependencies() {
    implementation(LibraryDependency.ROOM)
    kapt(LibraryDependency.KAPT_ROOM)
    implementation(LibraryDependency.ROOM_KTX)
}

fun DependencyHandler.addLifecycleDependencies() {
    implementation(LibraryDependency.ANDROIDX_VIEW_MODEL)
    implementation(LibraryDependency.ANDROIDX_LIVEDATA)
    implementation(LibraryDependency.ANDROIDX_SAVED_VIEW_MODEL)
    implementation(LibraryDependency.ANDROIDX_LIFE_CYCLE_RUNTIME)
    implementation(LibraryDependency.LIFECYCLE_COMPILER)
}

fun DependencyHandler.addJsonDependencies() {
    implementation(LibraryDependency.MOSHI)
    kapt(LibraryDependency.KAPT_MOSHI)
}

fun DependencyHandler.addNetworkDependencies() {
    implementation(LibraryDependency.OKHTTP)
    implementation(LibraryDependency.OKHTTP_LOGGING)
    implementation(LibraryDependency.RETROFIT)
    implementation(LibraryDependency.MOSHI_CONVERTER)
}