plugins {
    alias(libs.plugins.moodit.android.application.compose)
    alias(libs.plugins.moodit.hilt)
}

android {
    namespace = "com.swyp.moodit"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.compose.navigation)
    implementation(projects.feature.home)
    implementation(projects.feature.round)
    implementation(projects.feature.report)
    implementation(projects.feature.auth)
    implementation(projects.feature.onboard)

    implementation(projects.core.auth)
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(projects.core.network)
    implementation(projects.core.ui)

    implementation(libs.androidx.core.splashscreen)
}