package you.village.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import you.village.activity.main.category.CategoryBind
import you.village.activity.main.home.HomeBind
import you.village.activity.main.model.Main
import you.village.activity.main.notification.NotificationBind
import you.village.activity.main.profile.ProfileBind
import you.village.activity.main.schedule.ScheduleBind
import you.village.theme.MaterialBind
import you.village.theme.colors
import you.village.ui.fancybottombar.FancyBottomBar
import you.village.ui.fancybottombar.FancyColors
import you.village.ui.fancybottombar.FancyItem
import you.village.util.Database
import you.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainActivity : ComponentActivity() {

    private val vm = MainViewModel.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialBind {
                MainBind()
            }
        }
    }

    @Composable
    private fun MainBind() {
        var mainState by remember { mutableStateOf<Main>(Main.Home) }
        val fancyItems = listOf(
            FancyItem(icon = Icons.Outlined.Home, id = 0),
            FancyItem(icon = Icons.Outlined.Category, id = 1),
            FancyItem(icon = Icons.Outlined.Notifications, id = 2),
            FancyItem(icon = Icons.Outlined.EventNote, id = 3),
            FancyItem(icon = Icons.Outlined.Person, id = 4)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                Crossfade(mainState) { state ->
                    when (state) {
                        Main.Home -> HomeBind(this@MainActivity)
                        Main.Category -> CategoryBind()
                        Main.Notification -> NotificationBind()
                        Main.Calendar -> ScheduleBind()
                        Main.Profile -> ProfileBind()
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                FancyBottomBar(
                    items = fancyItems,
                    fancyColors = FancyColors(
                        primary = colors.primary
                    )
                ) {
                    when (id) {
                        0 -> mainState = Main.Home
                        1 -> mainState = Main.Category
                        2 -> mainState = Main.Notification
                        3 -> mainState = Main.Calendar
                        4 -> mainState = Main.Profile
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Database.upload(vm.me)
    }
}
