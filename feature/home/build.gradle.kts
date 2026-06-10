plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.home"
}

dependencies {
    implementation(projects.core.data)
    implementation(libs.bundles.coil)
}