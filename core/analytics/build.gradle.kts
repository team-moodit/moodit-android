plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.moodit.android.library.compose)
    alias(libs.plugins.moodit.hilt)
}

android {
    namespace = "com.swyp.moodit.analytics"
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}