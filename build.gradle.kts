// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //plugin name
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}