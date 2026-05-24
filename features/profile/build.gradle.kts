plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
}
android {
    namespace = "com.echo.features.profile"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
    implementation("androidx.navigation:navigation-compose:2.8.5")
    implementation(project(":core:utils"))
    implementation("io.coil-kt:coil-compose:2.6.0")
}