package you.village.ui.main.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.theme.typography
import you.village.ui.BaseActivity

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class DetailView : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setStatusBarColor(colors.primary)

        setContent {
            MaterialBind {
                DetailBind()
            }
        }
    }

    @Composable
    private fun DetailBind() {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = colors.primary,
                    title = { Text("물건 상세보기") },
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
            val imageUrls = remember { mutableStateListOf<String?>(null) }

            if (imageUrls.first() == null) {
                storage.reference.child("items/${vm.item.id}").listAll().addOnSuccessListener {
                    it.items.forEach { task ->
                        task.downloadUrl.addOnSuccessListener { uri ->
                            imageUrls.add(0, uri.toString())
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
            ) {
                if (imageUrls.first() == null) {
                    Image(
                        imageVector = Icons.Rounded.Downloading,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                } else {
                    Image(
                        painter = rememberGlidePainter(imageUrls.first()),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 8.dp, bottom = 55.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(
                            items = imageUrls.toList(),
                            itemContent = { url ->
                                if (url != null && url != imageUrls.first()) {
                                    Icon(
                                        painter = rememberGlidePainter(url),
                                        contentDescription = null,
                                        modifier = Modifier.size(150.dp)
                                    )
                                }
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(Color.Cyan, CircleShape)
                    )
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(75.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = vm.item.owner.name,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            text = vm.item.owner.locate,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                Text(text = vm.item.description, modifier = Modifier.padding(top = 10.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "대여비: ${
                        String.format(
                            "%,d",
                            vm.item.price
                        )
                        }/${vm.item.rentLength}달",
                        modifier = Modifier.padding(top = 2.dp)
                    )
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
                                text = "채팅하기",
                                style = typography.button,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = {
                            },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "대여 요청하기",
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
