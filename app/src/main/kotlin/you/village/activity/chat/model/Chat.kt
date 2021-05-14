package you.village.activity.chat.model

import you.village.activity.login.model.User
import java.util.Date

data class Chat(
    val id: String = "",
    val target: User = User(),
    val owner: User = User(),
    val message: String = "",
    val time: Long = Date().time,
    val attachment: Attachment? = null
)
