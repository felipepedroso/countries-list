import br.pedroso.citieslist.AndroidConfiguration

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    compileSdk = AndroidConfiguration.compileSdk

    defaultConfig {
        minSdk = AndroidConfiguration.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    namespace = "${AndroidConfiguration.applicationId}.databaseinitialization"
}

dependencies {
    api(project(":database"))
    testImplementation(project(":databasetest"))
    api(project(":datasource"))
    testImplementation(project(":datasourcetest"))
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.extensionsCompiler)
    api(libs.hilt.work)
    api(libs.androidx.room.runtime)
    implementation(libs.androidx.work.runtime.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.fixture)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
}
