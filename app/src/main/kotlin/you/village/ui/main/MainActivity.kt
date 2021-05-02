package you.village.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
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
import you.village.theme.MaterialBind
import you.village.theme.colors
import you.village.ui.main.model.Main
import you.village.ui.widget.fancybottombar.FancyBottomBar
import you.village.ui.widget.fancybottombar.FancyColors
import you.village.ui.widget.fancybottombar.FancyItem

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainActivity : ComponentActivity() {
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
            FancyItem(icon = Icons.Outlined.Person, id = 3)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) {
                Crossfade(mainState) { state ->
                    when (state) {
                        Main.Home -> HomeBind()
                        Main.Category -> CategoryBind()
                        Main.Notification -> TODO()
                        Main.Profile -> ProfileBind()
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
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
                        3 -> mainState = Main.Profile
                    }
                }
            }
        }
    }
}
