plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.moodit.hilt)
}

android {
    namespace = "com.swyp.moodit.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}