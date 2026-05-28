plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
    id("com.google.devtools.ksp")
}
android {
    namespace = "com.echo.features.category"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
}