package you.village.activity.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import you.village.MainViewModel
import you.village.activity.chat.ChatActivity
import you.village.activity.main.home.model.Item
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.theme.typography
import you.village.ui.GlideImage
import you.village.util.Database
import you.village.util.toast

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class DetailView : ComponentActivity() {

    private val vm = MainViewModel.instance
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setStatusBarColor(colors.primary)
        val itemId = intent.getStringExtra("itemId")!!
        item = vm.getItemFromId(itemId)

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
                    title = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text = "물건 상세보기")
                            if (vm.me.master) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            Database.remove(item)
                                            toast("삭제되었습니다.")
                                            finish()
                                        }
                                    )
                                }
                            }
                        }
                    },
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
                vm.storage.reference.child("items/${item.id}").listAll().addOnSuccessListener {
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageUrls.first() == null) {
                    Image(
                        imageVector = Icons.Rounded.Downloading,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                } else {
                    var scale by remember { mutableStateOf(1f) }
                    var rotation by remember { mutableStateOf(0f) }
                    var offset by remember { mutableStateOf(Offset.Zero) }
                    val state =
                        rememberTransformableState { zoomChange, offsetChange, rotationChange ->
                            scale *= zoomChange
                            rotation += rotationChange
                            offset += offsetChange
                        }

                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                rotationZ = rotation,
                                translationX = offset.x,
                                translationY = offset.y
                            )
                            .transformable(state = state),
                        src = imageUrls.first()!!
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
                                    GlideImage(
                                        modifier = Modifier.size(150.dp),
                                        src = url
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
                            text = item.owner.name,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        Text(
                            text = item.owner.locate,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
                Text(text = item.description, modifier = Modifier.padding(top = 10.dp))
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
                            item.price
                        )
                        }/${item.rentLength}달",
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(this@DetailView, ChatActivity::class.java)
                                intent.putExtra("itemId", item.id)
                                startActivity(intent)
                            },
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
