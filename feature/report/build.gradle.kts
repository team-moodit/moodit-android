plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.report"
}

dependencies {
    implementation(projects.core.data)
}