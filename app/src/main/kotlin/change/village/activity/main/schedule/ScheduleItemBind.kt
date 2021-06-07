package change.village.activity.main.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import change.village.activity.main.schedule.model.Schedule
import change.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/04.
 */

private val vm = MainViewModel.instance

@Composable
fun ScheduleItemBind(schedule: Schedule) {
    var isNotificationOn by remember { mutableStateOf(true) }
    val owner = vm.getUserFromUuid(schedule.item.ownerUuid)

    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .width(75.dp)
                    .height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color.Cyan, CircleShape)
                )
                Text(
                    text = schedule.item.name,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(schedule.name)
                Text("${owner.name} 님의 상품")
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = if (isNotificationOn) Icons.Outlined.Notifications else Icons.Outlined.NotificationsOff,
                contentDescription = null,
                modifier = Modifier.clickable { isNotificationOn = !isNotificationOn }
            )
        }
    }
}
