package you.village.ui.main.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.theme.typography
import you.village.ui.widget.RoundedTextField

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class ItemAddActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setStatusBarColor(colors.primary)

        setContent {
            MaterialBind {
                ItemAddBind()
            }
        }
    }

    @Composable
    private fun ItemAddBind() {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = colors.primary,
                    title = { Text("대여 상품 등록") },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { finish() }
                        )
                    }
                )
            }
        ) {
            val itemNameField = remember { mutableStateOf(TextFieldValue()) }
            val itemDescriptionField = remember { mutableStateOf(TextFieldValue()) }
            val itemPriceField = remember { mutableStateOf(TextFieldValue()) }
            val itemRentLengthField = remember { mutableStateOf(TextFieldValue()) }
            val itemImages = remember { mutableStateListOf("ADD") }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(16.dp))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("상품명")
                    RoundedTextField(
                        value = itemNameField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Text(text = "상품 상세 설명", modifier = Modifier.padding(top = 15.dp))
                    RoundedTextField(
                        value = itemDescriptionField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        isSingleLine = false
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("대여 비용")
                        RoundedTextField(
                            value = itemPriceField,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("대여 기한")
                        RoundedTextField(
                            value = itemRentLengthField,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp)
                        )
                    }
                    Text(text = "사진 등록", modifier = Modifier.padding(top = 15.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 8.dp, bottom = 55.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(
                            items = itemImages,
                            itemContent = { imageUrl ->
                                if (imageUrl == "ADD") {
                                    Icon(
                                        imageVector = Icons.Rounded.AddAPhoto,
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        painter = rememberGlidePainter(imageUrl),
                                        contentDescription = null,
                                        modifier = Modifier.size(150.dp)
                                    )
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(end = 15.dp)
                        ) {
                            Text(
                                text = "임시저장",
                                style = typography.button,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "등록하기",
                                style = typography.button,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
