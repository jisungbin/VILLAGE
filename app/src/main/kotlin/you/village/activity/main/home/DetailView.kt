package you.village.activity.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import you.village.activity.chat.ChatActivity
import you.village.activity.login.model.User
import you.village.activity.main.home.model.Item
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.theme.typography
import you.village.ui.GlideImage
import you.village.util.Database
import you.village.util.IntentKey
import you.village.util.toast
import you.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class DetailView : ComponentActivity() {

    private val vm = MainViewModel.instance
    private lateinit var item: Item
    private lateinit var owner: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setStatusBarColor(colors.primary)
        val itemUuid = intent.getStringExtra(IntentKey.ItemUuid)!!
        item = vm.getItemFromUuid(itemUuid)
        owner = vm.getUserFromUuid(item.ownerUuid)

        setContent {
            MaterialBind {
                DetailBind()
            }
        }
    }

    @Composable
    private fun DetailBind() {
        val imageUrls = vm.getImageUrlsFromUuid(item.uuid).observeAsState(SnapshotStateList())

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
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        src = imageUrls.value.first()
                    )
                    if (imageUrls.value.size > 1) {
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth()
                                .height(150.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
                        ) {
                            items(
                                items = imageUrls.value,
                                itemContent = { url ->
                                    if (url != imageUrls.value.first()) {
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
                            .padding(top = 30.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
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
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = owner.name,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                            Text(
                                text = owner.locate,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = item.description,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black)
                        )
                        Text(
                            text = "대여비: ${
                            String.format(
                                "%,d",
                                item.price
                            )
                            }/${item.rentLength}달",
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                        Row {
                            OutlinedButton(
                                onClick = {
                                    val intent = Intent(this@DetailView, ChatActivity::class.java)
                                    intent.putExtra(IntentKey.ItemUuid, item.uuid)
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
                                    // todo
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
}
