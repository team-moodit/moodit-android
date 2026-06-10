plugins {
    alias(libs.plugins.moodit.android.library)
}

android {
    namespace = "com.swyp.moodit.ui"
}

dependencies {
    api(projects.core.model)
    api(projects.core.designsystem)
    implementation(libs.bundles.coil)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}