plugins {
    alias(libs.plugins.moodit.android.library.compose)
}

android {
    namespace = "com.swyp.moodit.designsystem"
}

dependencies {
    implementation(libs.bundles.coil)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}