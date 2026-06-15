plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.moodit.hilt)
}

android {
    namespace = "com.swyp.moodit.core.auth"
}

dependencies {
    api(projects.core.model)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kakao.user)
}