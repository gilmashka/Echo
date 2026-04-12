import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.google.devtools.ksp.gradle.KspExtension

class DaggerModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            plugins.apply("com.google.devtools.ksp")

            dependencies {
                add("implementation", "com.google.dagger:dagger:2.52")
                add("ksp", "com.google.dagger:dagger-compiler:2.52")
            }
        }
    }
}