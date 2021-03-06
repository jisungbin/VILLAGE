package change.village.util

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

fun doDelay(ms: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ action() }, ms)
}

inline fun <reified T> Activity.open(activity: T, isActivityFinish: Boolean = true) {
    if (isActivityFinish) finish()
    startActivity(Intent(this, activity!!::class.java))
}

@Composable
fun fontResource(@FontRes font: Int) = FontFamily(Font(font))

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, length).show()
}
