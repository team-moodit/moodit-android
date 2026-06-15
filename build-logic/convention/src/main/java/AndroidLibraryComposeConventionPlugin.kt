import com.android.build.api.dsl.LibraryExtension
import com.swyp.moodit.convention.configureComposeAndroid
import com.swyp.moodit.convention.getLibrary
import com.swyp.moodit.convention.implementation
import com.swyp.moodit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("swyp.moodit.android.library")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureComposeAndroid(extension)

            dependencies {
                implementation(libs.getLibrary("timber"))
            }
        }
    }
}