plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.onboard"
}

dependencies {
    implementation(projects.core.data)
    implementation(libs.bundles.coil)
}