plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.tournament"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.analytics)
    implementation(libs.bundles.coil)
}