import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class NetworkModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            dependencies {
                add("api", "com.squareup.retrofit2:retrofit:2.11.0")
                add("api", "com.squareup.retrofit2:converter-gson:2.11.0")
                add("api", "com.squareup.okhttp3:okhttp:4.12.0")
                add("api", "com.squareup.okhttp3:logging-interceptor:4.12.0")
            }
        }
    }
}