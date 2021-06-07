package change.village.ui

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import change.village.GlideApp

/**
 * Created by Ji Sungbin on 2021/05/08.
 */

@Composable
fun GlideImage(modifier: Modifier, src: Any) {
    val context = LocalContext.current

    AndroidView(factory = { ImageView(context) }, modifier = modifier) {
        GlideApp.with(context).load(src).into(it)
    }
}
