package com.swyp.moodit.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension
) {
    commonExtension.apply {
        compileSdk = libs.getVersion("compileSdkVersion").requiredVersion.toInt()

        when (this) {
            is ApplicationExtension -> {
                defaultConfig {
                    minSdk = libs.getVersion("minSdkVersion").requiredVersion.toInt()
                    vectorDrawables.useSupportLibrary = true
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                    isCoreLibraryDesugaringEnabled = true
                }
            }

            is LibraryExtension -> {
                defaultConfig {
                    minSdk = libs.getVersion("minSdkVersion").requiredVersion.toInt()
                    vectorDrawables.useSupportLibrary = true
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                    isCoreLibraryDesugaringEnabled = true
                }
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
    }
}