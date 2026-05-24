import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class BaseAndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply("android-library")
            plugins.apply("kotlin-android")

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 35

                defaultConfig {
                    minSdk = 24
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }
        }
    }
}