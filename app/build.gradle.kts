plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.devtools.ksp)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.dagger.hilt)
  alias(libs.plugins.room)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.paparazzi)
}

android {
  namespace = "com.example.app"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.app"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "com.example.app.HiltTestRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  kotlin {
    compilerOptions {
      optIn.add("kotlin.time.ExperimentalTime")
      optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
      optIn.add("com.google.accompanist.permissions.ExperimentalPermissionsApi")
    }
  }
  room {
    schemaDirectory("$projectDir/schemas")
  }
}

dependencies {
  implementation(libs.bundles.android.x)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.compose.viewmodel)
  implementation(libs.bundles.navigation)
  implementation(libs.arrow.core)
  implementation(libs.dataStore)
  implementation(libs.accompanist.permissions)
  implementation(libs.bundles.kotlin.x)
  implementation(libs.bundles.ktor)
  implementation(libs.bundles.coil)

  // region Hilt
  implementation(libs.bundles.hilt)
  ksp(libs.hilt.compiler)
  // endregion

  // region Room
  implementation(libs.bundles.room)
  ksp(libs.room.compiler)
  // endregion

  // region UI testing
  androidTestImplementation(libs.bundles.ui.testing)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.hilt.android.testing)
  kspAndroidTest(libs.hilt.compiler)
  testImplementation(libs.bundles.ui.testing)
  // endregion

  // region Screenshot testing
  testImplementation(libs.paparazzi)
  // endregion

  debugImplementation(libs.androidx.ui.test.manifest)
  debugImplementation(libs.androidx.ui.tooling)

  // region Unit testing
  testImplementation(libs.bundles.unit.testing)
  // endregion
}