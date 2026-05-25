plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
}

android {
    namespace = "com.echo.features.event"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
    implementation(project(":core:utils"))
    implementation("androidx.navigation:navigation-compose:2.8.5")
}