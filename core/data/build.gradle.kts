plugins {
    alias(libs.plugins.moodit.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.moodit.hilt)
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
    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.paging.common)
}