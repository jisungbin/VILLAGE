package you.village.activity.main.home

import android.app.Activity
import android.content.Intent
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import you.village.R
import you.village.activity.main.home.model.Item
import you.village.theme.colors
import you.village.ui.GlideImage
import you.village.ui.RoundedTextField
import you.village.util.Database
import you.village.util.IntentKey
import you.village.util.open
import you.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

private val vm = MainViewModel.instance

@Composable
fun HomeBind(activity: Activity) {
    val searchField = remember { mutableStateOf(TextFieldValue()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(isRefreshing.value)
    val items = vm.items.observeAsState(SnapshotStateList())

    val animationSpec =
        remember { LottieAnimationSpec.RawRes(R.raw.empty_item) }
    val animationState = rememberLottieAnimationState(
        autoPlay = true,
        repeatCount = Integer.MAX_VALUE
    )

    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = { Database.loadItems() }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedTextField(
                    value = searchField,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        )
                        .fillMaxWidth(),
                    placeholder = "검색어 입력"
                )
                if (items.value.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            spec = animationSpec,
                            animationState = animationState,
                            modifier = Modifier.size(250.dp)
                        )
                        Text(
                            text = "올라온 제품이 없어요 :(",
                            modifier = Modifier.padding(top = 30.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 16.dp
                        )
                    ) {
                        items(items.value) { item ->
                            ItemBind(activity, item)
                        }
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = { activity.open(ItemAddActivity(), false) }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = colors.primary
            )
        }
    }
}

@Composable
private fun ItemBind(activity: Activity, item: Item) {
    var likeState by remember { mutableStateOf(vm.me.likeItem.contains(item.uuid)) }
    val imageUrls = vm.getImageUrlsFromUuid(item.uuid).observeAsState(SnapshotStateList())

    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.downloading) }
    val animationState =
        rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                val intent = Intent(activity, DetailView::class.java)
                intent.putExtra(IntentKey.ItemUuid, item.uuid)
                activity.startActivity(intent)
            }
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUrls.value.isEmpty()) {
            LottieAnimation(
                spec = animationSpec,
                animationState = animationState,
                modifier = Modifier.size(150.dp)
            )
        } else {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                src = imageUrls.value.first()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
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
                            val newLikeItems = mutableListOf<String>()
                            if (likeState) {
                                item.likeCount--
                                newLikeItems.run {
                                    addAll(vm.me.likeItem)
                                    remove(item.uuid)
                                }
                            } else {
                                item.likeCount++
                                newLikeItems.run {
                                    addAll(vm.me.likeItem)
                                    add(item.uuid)
                                }
                            }
                            vm.me = vm.me.copy(likeItem = newLikeItems.toList())
                            likeState = !likeState

                            Database.upload(item)
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
