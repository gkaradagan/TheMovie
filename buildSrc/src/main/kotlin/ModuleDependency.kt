import kotlin.reflect.full.memberProperties

// "Module" means "project" in terminology of Gradle API. To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object ModuleDependency {
    // All consts are accessed via reflection
    const val CORE = ":library:core"
    const val APP = ":app"
    const val COMMON = ":feature:common"

    const val FEATURE_MOVIE = ":feature:popularmovie"
    const val FEATURE_SERIES = ":feature:popularseries"
    const val FEATURE_FAVOURITE = ":feature:favourite"
}
