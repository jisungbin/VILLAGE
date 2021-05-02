package you.village.ui.widget

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun WidthSpace(width: Dp) {
    Text(text = " ", modifier = Modifier.width(width))
}

@Composable
fun HeightSpace(height: Dp) {
    Text(text = " ", modifier = Modifier.height(height))
}