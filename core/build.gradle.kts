plugins {
    id("base-android-library")
}

android {
    namespace = "com.echo.core"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}