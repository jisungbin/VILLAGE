package you.village.activity.main.schedule

import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import you.village.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

private val vm = MainViewModel.instance

@Composable
fun ScheduleBind() {
    val context = LocalContext.current
//    val scheduleItems = listOf(
//        Schedule(
//            id = "",
//            owner = vm.me,
//            target = User(),
//            name = "tests",
//            item = Item(name = "TEST1", owner = User(name = "성빈"))
//        ),
//        Schedule(
//            id = "",
//            owner = vm.me,
//            target = User(),
//            name = "tests",
//            item = Item(name = "TEST2", owner = User(name = "성빈2"))
//        ),
//        Schedule(
//            id = "",
//            owner = vm.me,
//            target = User(),
//            name = "tests",
//            item = Item(name = "TEST3", owner = User(name = "성빈3"))
//        )
//    )
    val scheduleItems = listOf<Schedule>()

    Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        AndroidView(
            factory = { CalendarView(context) },
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth()
        )
        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(text = "다가오는 일정")
            LazyColumn(modifier = Modifier.padding(top = 8.dp).fillMaxSize()) {
                items(items = scheduleItems, itemContent = { ScheduleItemBind(it) })
            }
        }
    }
}
