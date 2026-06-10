plugins {
    alias(libs.plugins.moodit.android.library)
}

android {
    namespace = "com.swyp.moodit.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}