plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
    id("name.remal.check-dependency-updates") version "1.3.1"
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
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
    implementation(platform(Dependencies.FirebaseBom))

    Dependencies.Firebase.forEach { def -> implementation(def) }
    Dependencies.Essential.forEach { def -> implementation(def) }
    Dependencies.Network.forEach { def -> implementation(def) }
    Dependencies.Ui.forEach { def -> implementation(def) }
    Dependencies.Util.forEach { def -> implementation(def) }
    Dependencies.Compose.forEach { def -> implementation(def) }
    Dependencies.Room.forEach { def -> implementation(def) }
    Dependencies.Compiler.forEach { def -> kapt(def) }
}
