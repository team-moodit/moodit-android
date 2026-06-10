plugins {
    alias(libs.plugins.moodit.android.library)
}

android {
    namespace = "com.swyp.moodit.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    api(projects.core.datastore)
    api(projects.core.network)
    api(projects.core.auth)
    implementation(libs.bundles.paging)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}