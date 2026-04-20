plugins {
    id("base-android-library")
    id("network-module")
    id("dagger-module")
}

android {
    namespace = "com.echo.core.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}