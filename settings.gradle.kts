pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Moodit"
include(":app")
include(":feature:home")
include(":core:data")
include(":core:network")
include(":core:datastore")
include(":core:model")
include(":core:navigation")
include(":core:designsystem")
include(":core:common")
include(":core:auth")
include(":core:ui")
include(":feature:round")
include(":feature:report")
include(":feature:auth")
