package you.village.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import you.village.model.User
import you.village.theme.colors
import you.village.ui.chat.model.Chat

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

@Composable
fun ChatBubbleBind(chat: Chat, me: User) {
    fun isMyChat() = chat.owner.id == me.id

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = if (!isMyChat()) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isMyChat()) {
            val shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomEnd = 10.dp
            )
            /*Icon(
                painter = rememberGlidePainter(chat.owner.profileImageUrl),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )*/
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan, CircleShape)
            )
            Text(
                text = chat.message,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(shape)
                    .wrapContentSize()
                    .border(1.dp, colors.primary, shape)
                    .padding(10.dp)
            )
        } else {
            val shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 10.dp
            )

            Text(
                text = chat.message,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(shape)
                    .wrapContentSize()
                    .border(1.dp, colors.primary, shape)
                    .padding(10.dp)
            )
            /*Icon(
                painter = rememberGlidePainter(chat.owner.profileImageUrl),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )*/
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Cyan, CircleShape)
            )
        }
    }
}
