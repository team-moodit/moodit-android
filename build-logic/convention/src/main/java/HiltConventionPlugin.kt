import com.swyp.moodit.convention.getLibrary
import com.swyp.moodit.convention.implementation
import com.swyp.moodit.convention.ksp
import com.swyp.moodit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.application") {
                pluginManager.apply("com.google.devtools.ksp")
                pluginManager.apply("com.google.dagger.hilt.android")
                dependencies {
                    implementation(libs.getLibrary("hilt.android"))
                    ksp(libs.getLibrary("hilt.android.compiler"))
                    ksp(libs.getLibrary("kotlin-metadata-jvm"))
                }
            }

            pluginManager.withPlugin("com.android.library") {
                pluginManager.apply("com.google.devtools.ksp")
                pluginManager.apply("com.google.dagger.hilt.android")
                dependencies {
                    implementation(libs.getLibrary("hilt.android"))
                    ksp(libs.getLibrary("hilt.android.compiler"))
                    ksp(libs.getLibrary("kotlin-metadata-jvm"))
                }
            }

            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                dependencies {
                    implementation(libs.getLibrary("hilt.core"))
                }
            }
        }
    }
}
