plugins {
    id("base-android-library")
    id("compose-module")
}

android {
    namespace = "com.echo.core.uikit"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.coil.compose)

    implementation(project(":core:network"))
}