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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Sajili"
include(":app")
include(":core:common")
include(":ui")
include(":core:ui")
include(":core:data")
include(":core:network")
include(":core:database")
include(":features")
include(":features:onboarding")
include(":features:document_capture")
