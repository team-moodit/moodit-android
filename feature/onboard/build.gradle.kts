import org.gradle.kotlin.dsl.projects

plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.onboard"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.analytics)
    implementation(libs.bundles.coil)
}