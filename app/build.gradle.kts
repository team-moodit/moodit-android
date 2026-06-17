import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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

    defaultConfig {
        val kakaoClientKey =
            gradleLocalProperties(rootDir, providers).getProperty("KAKAO_CLIENT_KEY") ?: ""
        if (kakaoClientKey.isEmpty()) {
            throw GradleException("KAKAO_CLIENT_KEY is not set.")
        }
        buildConfigField("String", "KAKAO_CLIENT_KEY", "\"$kakaoClientKey\"")
        manifestPlaceholders["KAKAO_CLIENT_KEY"] = kakaoClientKey
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.compose.navigation)
    implementation(projects.feature.home)
    implementation(projects.feature.tournament)
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
    implementation(libs.kakao.user)
}