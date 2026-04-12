import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


class BaseAndroidApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply("com.android.application")
            plugins.apply("org.jetbrains.kotlin.android")

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = 35

                defaultConfig {
                    applicationId = "com.echo"
                    minSdk = 24
                    targetSdk = 35
                    versionCode = 1
                    versionName = "1.0"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                    }
                }
            }
        }
    }
}