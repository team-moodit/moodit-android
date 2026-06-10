plugins {
    alias(libs.plugins.moodit.android.library)
}

android {
    namespace = "com.swyp.moodit.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.model)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}