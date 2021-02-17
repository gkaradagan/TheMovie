package plugins

import com.vanniktech.android.junit.jacoco.JunitJacocoExtension
import Versions

plugins.apply("com.vanniktech.android.junit.jacoco")

configure<JunitJacocoExtension> {
    jacocoVersion = Versions.JACOCO_INTERNAL
    excludes = listOf(
        "**/*_Provide*/**",
        "**/*_Factory*/**",
        "**/*_MembersInjector.class",
        "**/*Dagger*"
    )
    includeNoLocationClasses = true
    includeInstrumentationCoverageInMergedReport = false
}
