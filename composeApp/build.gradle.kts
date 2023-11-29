import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.kotlinMultiplatform)

  alias(libs.plugins.jetbrainsCompose)
}

kotlin {
  jvm("desktop")

  sourceSets {
    val desktopMain by getting

    desktopMain.dependencies {
      implementation(compose.desktop.currentOs)
    }
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material)
      @OptIn(ExperimentalComposeLibrary::class)
      implementation(compose.components.resources)
      api("moe.tlaster:precompose:1.5.7")
      api("moe.tlaster:precompose-viewmodel:1.5.7")
      api("moe.tlaster:precompose-molecule:1.5.7")
      api("moe.tlaster:precompose-koin:1.5.7")
    }
  }
}


compose.desktop {
  application {
    mainClass = "boxtest.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "org.vorgi"
      packageVersion = "1.0.0"
    }
  }
}
