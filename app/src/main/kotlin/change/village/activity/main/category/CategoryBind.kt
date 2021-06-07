package change.village.activity.main.category

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import change.village.R
import change.village.activity.main.home.DetailView
import change.village.activity.main.home.model.Item
import change.village.theme.colors
import change.village.ui.GlideImage
import change.village.util.Database
import change.village.util.IntentKey
import change.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

private val vm = MainViewModel.instance

@Composable
fun CategoryBind(activity: Activity) {
    val categories = listOf(
        Category.None,
        Category.Electronics,
        Category.Computer,
        Category.Book,
        Category.Appliance
    )
    val focusedCategoryItem = remember { mutableStateOf(categories.first()) }

    val animationSpec =
        remember { LottieAnimationSpec.RawRes(R.raw.empty_item) }
    val animationState = rememberLottieAnimationState(
        autoPlay = true,
        repeatCount = Integer.MAX_VALUE
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(
                items = categories,
                itemContent = { category ->
                    fun isItemFocusing() = focusedCategoryItem.value == category
                    val itemFontColor =
                        animateColorAsState(if (isItemFocusing()) Color.White else Color.Black)
                    val itemBackgroundColor =
                        animateColorAsState(if (isItemFocusing()) colors.primary else Color.White)

                    Surface(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .clickable { focusedCategoryItem.value = category },
                        shape = RoundedCornerShape(16.dp),
                        elevation = 2.dp,
                        color = itemBackgroundColor.value
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                            color = itemFontColor.value
                        )
                    }
                }
            )
        }
        Crossfade(
            targetState = focusedCategoryItem.value,
            modifier = Modifier.fillMaxSize()
        ) { category ->
            val categoryItems =
                vm.items.observeAsState(SnapshotStateList()).value.filter { it.category == category }

            if (categoryItems.isEmpty()) {
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
                    items(categoryItems) { item ->
                        ItemBind(activity = activity, item = item)
                    }
                }
            }
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
