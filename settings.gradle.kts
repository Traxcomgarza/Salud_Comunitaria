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

rootProject.name = "Salud_Comunitaria"
include(":app")
include(":data-core")
include(":feature-auth")
include(":feature-medical-history")
include(":feature-show-diseases")
include(":ui-resources")
include(":feature-profile")
include(":feature-admin")
include(":feature-suggestion")
