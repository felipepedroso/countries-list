pluginManagement {
    repositories {
        google()
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
rootProject.name = "CitiesList"
include(":app")
include(":domain")
include(":designsystem")
include(":datasource")
include(":database")
include(":databaseinitialization")
include(":repository")
include(":features:citymap")
include(":features:starredcities")
include(":features:citiessearch")
