plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.20")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.1.20")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.20-1.0.32")
}

gradlePlugin {
    plugins {
        register("baseAndroidLibrary") {
            id = "base-android-library"
            implementationClass = "BaseAndroidLibraryPlugin"
        }

        register("baseAndroidApplication") {
            id = "base-android-application"
            implementationClass = "BaseAndroidApplicationPlugin"
        }

        register("composeModule") {
            id = "compose-module"
            implementationClass = "ComposeModulePlugin"
        }

        register("networkModule") {
            id = "network-module"
            implementationClass = "NetworkModulePlugin"
        }

        register("daggerModule") {
            id = "dagger-module"
            implementationClass = "DaggerModulePlugin"
        }

        register("composeApplication") {
            id = "compose-application"
            implementationClass = "ComposeApplicationPlugin"
        }
    }
}