plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.round"
}

dependencies {
    implementation(projects.core.data)
}