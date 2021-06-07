import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30
    const val versionCode = 1
    const val jvmTarget = "1.8"
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    object Essential {
        const val Kotlin = "1.4.32"
        const val Gradle = "7.1.0-alpha01"
        const val CoreKtx = "1.3.2"
        const val GoogleServices = "4.3.5"
    }

    object Ui {
        const val Lottie = "1.0.0-alpha07-SNAPSHOT"
        const val Glide = "4.12.0"
        const val ImagePicker = "1.4.3"
        const val SwipeRefresh = "0.8.1"
    }

    object Util {
        const val CrashReporter = "1.1.0"
        const val Gson = "2.8.6"
    }

    object Google {
        const val Location = "18.0.0"
        const val Map = "17.0.0"
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
    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"

    val Firebase = listOf(
        "com.google.firebase:firebase-firestore-ktx",
        "com.google.firebase:firebase-auth-ktx",
        "com.google.firebase:firebase-storage-ktx",
        "com.google.firebase:firebase-database-ktx"
    )

    val Classpath = listOf(
        "com.android.tools.build:gradle:${Versions.Essential.Gradle}",
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}",
        "com.google.gms:google-services:${Versions.Essential.GoogleServices}"
    )

    val Network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val Google = listOf(
        "com.google.android.gms:play-services-location:${Versions.Google.Location}",
        "com.google.android.gms:play-services-maps:${Versions.Google.Map}"
    )

    val Essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
    )

    val Ui = listOf(
        "com.github.bumptech.glide:glide:${Versions.Ui.Glide}",
        "com.airbnb.android:lottie-compose:${Versions.Ui.Lottie}",
        "com.github.nguyenhoanglam:ImagePicker:${Versions.Ui.ImagePicker}",
        "com.google.accompanist:accompanist-swiperefresh:${Versions.Ui.SwipeRefresh}"
    )

    val Util = listOf(
        "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}",
        "com.squareup.retrofit2:converter-gson:${Versions.Network.Retrofit}",
        "com.google.code.gson:gson:${Versions.Util.Gson}"
    )

    var Room = listOf(
        "androidx.room:room-runtime:${Versions.Jetpack.Room}",
        "androidx.room:room-ktx:${Versions.Jetpack.Room}"
    )

    var Compose = listOf(
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Version}",
        "androidx.compose.ui:ui:${Versions.Compose.Version}",
        "androidx.compose.material:material:${Versions.Compose.Version}",
        "androidx.compose.material:material-icons-extended:${Versions.Compose.Version}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Version}"
    )

    val Compiler = listOf(
        "androidx.room:room-compiler:${Versions.Jetpack.Room}",
        "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
    )
}
