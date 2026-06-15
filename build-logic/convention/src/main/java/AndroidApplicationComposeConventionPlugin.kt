import com.android.build.api.dsl.ApplicationExtension
import com.swyp.moodit.convention.configureComposeAndroid
import com.swyp.moodit.convention.getLibrary
import com.swyp.moodit.convention.implementation
import com.swyp.moodit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply("swyp.moodit.android.application")
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureComposeAndroid(extension)
            dependencies {
                implementation(libs.getLibrary("timber"))
            }
        }
    }
}