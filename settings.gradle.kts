rootProject.name = "text-display-api"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.projectshard.dev/repository/maven-public/")
    }
    plugins {
        kotlin("jvm") version "2.1.10"
    }
}