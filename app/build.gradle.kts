plugins {
    id("base-android-application")
    id("compose-application")
    id("dagger-module")
}

android {
    namespace = "com.echo"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.activity.compose)
    implementation(project(":features:auth"))
    implementation(project(":core:uikit"))
    implementation(project(":core:network"))
}