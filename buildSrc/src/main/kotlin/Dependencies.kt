import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val versionCode = 1
    const val jvmTarget = "1.8"
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Essential {
        const val Kotlin = "1.4.32"
        const val Gradle = "7.0.0-alpha15"
        const val CoreKtx = "1.3.2"
        const val GoogleServices = "4.3.5"
    }

    object Ui {
        const val Lottie = "1.0.0-alpha07-SNAPSHOT"
        const val Glide = "0.8.1"
    }

    object Util {
        const val CrashReporter = "1.1.0"
        const val Gson = "2.8.6"
    }

    object Firebase {
        const val Bom = "27.1.0"
    }

    object Network {
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Room = "2.3.0"
    }

    object Compose {
        const val Version = "1.0.0-beta04"
        const val Activity = "1.3.0-alpha05"
    }
}

object Dependencies {
    const val firebase = "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"

    val classpath = listOf(
        "com.android.tools.build:gradle:${Versions.Essential.Gradle}",
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}",
        "com.google.gms:google-services:${Versions.Essential.GoogleServices}"
    )

    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
    )

    val ui = listOf(
        "com.google.accompanist:accompanist-glide:${Versions.Ui.Glide}",
        "com.airbnb.android:lottie-compose:${Versions.Ui.Lottie}"
    )

    val util = listOf(
        "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}",
        "com.squareup.retrofit2:converter-gson:${Versions.Network.Retrofit}",
        "com.google.code.gson:gson:${Versions.Util.Gson}"
    )

    var room = listOf(
        "androidx.room:room-runtime:${Versions.Jetpack.Room}",
        "androidx.room:room-ktx:${Versions.Jetpack.Room}"
    )

    var compose = listOf(
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Version}",
        "androidx.compose.ui:ui:${Versions.Compose.Version}",
        "androidx.compose.material:material:${Versions.Compose.Version}",
        "androidx.compose.material:material-icons-extended:${Versions.Compose.Version}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Version}"
    )

    val compiler = listOf(
        "androidx.room:room-compiler:${Versions.Jetpack.Room}"
    )
}
