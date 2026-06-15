plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.feature.auth"
}

dependencies {
    implementation(projects.core.data)
}