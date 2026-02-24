// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.dagger.hilt) apply false
  alias(libs.plugins.room) apply false
  alias(libs.plugins.devtools.ksp) apply false
}

// Work around Paparazzi + Gradle 9 HTML report incompatibility.
// See: https://github.com/cashapp/paparazzi/issues/2111
subprojects {
  tasks.withType<org.gradle.api.tasks.testing.Test>().configureEach {
    reports.html.required.set(false)
  }
}