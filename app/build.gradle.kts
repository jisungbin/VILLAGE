plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("name.remal.check-dependency-updates") version "1.3.1"
}

android {
    compileSdkVersion(Application.compileSdk)

    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName ($versionCode)")

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildFeatures {
        compose = true
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Version
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
            resources.excludes.add("META-INF/library_release.kotlin_module")
        }
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
        useIR = true
    }
}

dependencies {
    Dependencies.essential.forEach { def -> implementation(def) }
    Dependencies.network.forEach { def -> implementation(def) }
    Dependencies.ui.forEach { def -> implementation(def) }
    Dependencies.util.forEach { def -> implementation(def) }
    Dependencies.compose.forEach { def -> implementation(def) }
    Dependencies.room.forEach { def -> implementation(def) }
    Dependencies.compiler.forEach { def -> kapt(def) }
}