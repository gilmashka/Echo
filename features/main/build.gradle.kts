plugins {
    id("base-android-library")
    id("compose-module")
    id("dagger-module")
}

android {
    namespace = "com.echo.features.main"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
    implementation(project(":features:feed"))
    implementation(project(":features:category"))
    implementation(project(":features:event"))
    implementation(project(":features:profile"))
    implementation(project(":features:friends"))
}