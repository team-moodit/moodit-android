plugins {
    alias(libs.plugins.moodit.android.library)
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

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}