import kotlin.reflect.full.memberProperties

// "Module" means "project" in terminology of Gradle API. To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object ModuleDependency {
    // All consts are accessed via reflection
    const val APP = ":app"
    const val CORE = ":library:core"
    const val NAVIGATION = ":library:navigation"


    const val FEATURE_MOVIE = ":feature:popularmovie"
    const val FEATURE_SERIES = ":feature:popularseries"
    const val FEATURE_FAVOURITE = ":feature:favourite"


    // False positive" function can be private"
    // See: https://youtrack.jetbrains.com/issue/KT-33610
    fun getAllModules() = ModuleDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    fun getAllFeatureModules() = ModuleDependency::class.memberProperties
        .filter { it.isConst and it.name.contains("feature") }
        .map { it.getter.call().toString() }
        .toList()
}
