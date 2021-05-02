package you.village.ui.main

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import you.village.model.Item
import you.village.model.User
import you.village.theme.colors
import java.util.Date

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

@Composable
fun HomeBind() {
    val items = remember {
        mutableStateListOf(
            Item(
                id = 0,
                name = "Test",
                imageUrl = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcA4Erc%2Fbtq3JS5DxgZ%2Fkbr4PKkYfZkg6KrzKx99H1%2Fimg.png",
                likeCount = 0,
                price = 1000000000,
                rentLength = 0,
                discountPercentage = 0,
                owner = User(
                    id = "test",
                    name = "SSSSSS",
                    profileImageUrl = "",
                    likeItem = listOf(),
                    wrotePost = listOf(),
                    uploadItem = listOf()
                ),
                uploadDate = Date().time
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        ) {
            items(
                items = items,
                itemContent = { item ->
                    ItemBind(item)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                onClick = {
                    items.addAll(items)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = colors.primary
                )
            }
        }
    }
}

@Composable
private fun ItemBind(item: Item) {
    var likeState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp),
    ) {
        Image(
            painter = rememberGlidePainter(item.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Text(item.name)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item.likeCount.toString())
                Icon(
                    imageVector = if (likeState) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = null,
                    tint = if (likeState) Color.Red else Color.Black,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            if (likeState) {
                                item.likeCount--
                            } else {
                                item.likeCount++
                            }
                            likeState = !likeState
                        }
                )
            }
        }
        Text(
            text = "대여비: ${String.format("%,d", item.price)}/${item.rentLength}달",
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
