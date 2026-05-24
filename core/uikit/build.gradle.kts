plugins {
    id("base-android-library")
    id("compose-module")
}

android {
    namespace = "com.echo.core.uikit"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    api("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation(project(":core:network"))
}