package you.village.activity.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material.icons.outlined.Try
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import you.village.viewmodel.MainViewModel
import you.village.theme.typography

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

private val vm = MainViewModel.instance

@Composable
fun ProfileBind() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Cyan)
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${vm.me.name} 님", modifier = Modifier.padding(bottom = 4.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "프로필 수정",
                        style = typography.button,
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.size(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "찜한 물건", modifier = Modifier.padding(top = 4.dp))
            }
            Column(
                modifier = Modifier.size(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.SupervisorAccount,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "대여 내역 관리",
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.size(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "위치 수정", modifier = Modifier.padding(top = 4.dp))
            }
        }
        Spacer(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "설정", modifier = Modifier.padding(start = 4.dp))
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Article,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "내 대여품 관리", modifier = Modifier.padding(start = 4.dp))
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Try,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "후기", modifier = Modifier.padding(start = 4.dp))
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(text = "출석체크", modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}
