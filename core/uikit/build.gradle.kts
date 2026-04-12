plugins {
    id("base-android-library")
    id("compose-module")
}

android {
    namespace = "com.echo.core.uikit"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}