import com.swyp.moodit.convention.androidTestImplementation
import com.swyp.moodit.convention.getBundle
import com.swyp.moodit.convention.getLibrary
import com.swyp.moodit.convention.implementation
import com.swyp.moodit.convention.libs
import com.swyp.moodit.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("swyp.moodit.android.library.compose")
                apply("swyp.moodit.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation(project((":core:designsystem")))
                implementation(project(":core:ui"))
                implementation(project(":core:navigation"))
                implementation(project(":core:common"))

                implementation(libs.getLibrary("kotlinx.serialization.json"))
                implementation(libs.getBundle("navigation"))
                implementation(libs.getBundle("compose"))
                implementation(libs.getBundle("paging"))
                implementation(libs.getBundle("test"))

                implementation(libs.getLibrary("timber"))
            }
        }
    }
}