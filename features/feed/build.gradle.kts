plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
}

android {
    namespace = "com.echo.features.feed"
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
}