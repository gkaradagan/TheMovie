rootProject.name = "TheMovie"
rootProject.buildFileName = "build.gradle.kts"

rootDir
    .walk()
    .maxDepth(2)
    .filter {
        it.name != "buildSrc" &&
            it.isDirectory &&
            file("${it.absolutePath}/build.gradle.kts").exists()
    }
    .forEach {
        when (it.parentFile.name) {
            "library" -> {
                include(":library:${it.name}")
            }
            "feature" -> {
                include(":feature:${it.name}")
            }
            else -> {
                include(":${it.name}")
            }
        }
    }
