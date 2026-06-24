plugins {
    alias(libs.plugins.moodit.android.feature)
}

android {
    namespace = "com.swyp.moodit.home"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val versionName = rootProject.extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")
            .findVersion("projectVersionName")
            .get()
            .toString()
        buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(libs.bundles.coil)
}