plugins {
    alias(libs.plugins.moodit.android.library.compose)
}

android {
    namespace = "com.swyp.moodit.designsystem"
}

dependencies {
    implementation(libs.bundles.coil)
}