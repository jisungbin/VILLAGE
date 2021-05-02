package you.village.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTextField(
    value: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        singleLine = true,
        placeholder = { if (placeholder.isNotBlank()) Text(placeholder) },
        maxLines = 1,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(20.dp)),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.White,
            cursorColor = Color.Black
        )
    )
}
