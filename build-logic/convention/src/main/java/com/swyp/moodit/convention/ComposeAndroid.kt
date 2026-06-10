package com.swyp.moodit.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid(
    commonExtension: CommonExtension
) {
    commonExtension.apply {
        buildFeatures.compose = true

        dependencies {
            val composeBom = libs.getLibrary("androidx-compose-bom")
            implementation(platform(composeBom))
            implementation(libs.getBundle("compose"))
            debugImplementation(libs.getBundle("compose-debug"))
        }
    }

    pluginManager.apply(libs.findPlugin("compose-compiler").get().get().pluginId)
}