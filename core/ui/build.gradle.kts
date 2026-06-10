plugins {
    alias(libs.plugins.moodit.android.library.compose)
}

android {
    namespace = "com.swyp.moodit.ui"
}

dependencies {
    api(projects.core.model)
    api(projects.core.designsystem)
    implementation(libs.bundles.coil)
}