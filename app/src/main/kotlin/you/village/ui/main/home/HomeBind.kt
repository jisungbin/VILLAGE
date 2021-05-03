package you.village.ui.main.home

import android.app.Activity
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Downloading
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import you.village.model.Item
import you.village.theme.colors
import you.village.ui.widget.RoundedTextField
import you.village.util.DataUtil
import you.village.util.open
import you.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

@Composable
fun HomeBind(
    activity: Activity,
    firestore: FirebaseFirestore,
    storage: FirebaseStorage,
    vm: MainViewModel,
) {
    val searchField = remember { mutableStateOf(TextFieldValue()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(isRefreshing.value)
    val items = remember { mutableStateListOf<Item>() }

    fun loadItems() {
        items.clear()
        firestore.collection("items").get().addOnSuccessListener { task ->
            task.documents.forEach { documentSnapshot ->
                items.add(documentSnapshot.toObject(Item::class.java)!!)
            }
        }
    }

    loadItems()
    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = { loadItems() }
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    )
                ) {
                    items(
                        items = items.toList(),
                        itemContent = { item ->
                            ItemBind(activity, item, storage, firestore, vm)
                        }
                    )
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
}

@Composable
private fun ItemBind(
    activity: Activity,
    item: Item,
    storage: FirebaseStorage,
    firestore: FirebaseFirestore,
    vm: MainViewModel,
) {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var likeState by remember { mutableStateOf(vm.me.likeItem.contains(item.id)) }

    if (imageUrl == null) {
        storage.reference.child("items/${item.id}").listAll().addOnSuccessListener {
            it.items.first().downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                vm.item = item
                activity.open(DetailView(), false)
            }
            .padding(top = 8.dp),
    ) {
        if (imageUrl == null) {
            Image(
                imageVector = Icons.Rounded.Downloading,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        } else {
            Image(
                painter = rememberGlidePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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
                                    remove(item.id)
                                }
                            } else {
                                item.likeCount++
                                newLikeItems.run {
                                    addAll(vm.me.likeItem)
                                    add(item.id)
                                }
                            }
                            DataUtil.itemReUpload(item, firestore)
                            vm.me = vm.me.copy(likeItem = newLikeItems.toList())
                            DataUtil.userReUpload(vm.me, firestore)
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
