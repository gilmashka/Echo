plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
}

android {
    namespace = "com.echo.features.friends"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
}