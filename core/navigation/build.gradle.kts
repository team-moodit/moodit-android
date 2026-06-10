plugins {
    alias(libs.plugins.moodit.android.library.compose)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.swyp.moodit.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.model)
}