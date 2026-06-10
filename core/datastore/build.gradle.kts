plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.moodit.hilt)
}

android {
    namespace = "com.swyp.moodit.datastore"
}

dependencies {
    api(projects.core.model)
    api(libs.androidx.dataStore)
    api(libs.androidx.dataStore.preferences)
    implementation(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
}