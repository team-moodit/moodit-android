plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.moodit.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.swyp.moodit.network"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    implementation(projects.core.datastore)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.okhttp)
    api(libs.bundles.retrofit)
    implementation(libs.bundles.gson)
    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.core)
    testImplementation(libs.coroutines.test)
}