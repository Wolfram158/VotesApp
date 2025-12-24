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

rootProject.name = "Vote"
include(":app")
include(":common")
include(":feature")
include(":feature:create-vote")
include(":feature:gateway")
include(":feature:refresh-with-email-code")
include(":feature:registration-with-email-code")
include(":feature:registration-for-emai-code")
include(":feature:vote")
include(":votes")
include(":feature:votes")
