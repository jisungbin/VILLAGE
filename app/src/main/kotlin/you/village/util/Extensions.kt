package you.village.util

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

fun doDelay(ms: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        {
            action()
        },
        ms
    )
}

inline fun <reified T> Activity.open(activity: T) {
    finish()
    startActivity(Intent(this, activity!!::class.java))
}

@Composable
fun fontResource(@FontRes font: Int) = FontFamily(Font(font))
