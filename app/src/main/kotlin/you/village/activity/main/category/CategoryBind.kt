package you.village.activity.main.category

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import you.village.MainViewModel
import you.village.theme.colors

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

private val vm = MainViewModel.instance

@Composable
fun CategoryBind() {
    val categorys = listOf(
        Category.None,
        Category.Electronics,
        Category.Computer,
        Category.Book,
        Category.Appliance
    )
    val focusedCategoryItem = remember { mutableStateOf(categorys.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, end = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(
                items = categorys,
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
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Crossfade(targetState = focusedCategoryItem.value) { category ->
                val categoryItems = vm.items.filter { it.category == category }
            }
        }
    }
}
