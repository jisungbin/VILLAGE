package you.village.ui.chat.model

import you.village.model.User

data class Chat(
    val id: String = "",
    val target: User = User(),
    val owner: User = User(),
    val message: String = "",
    val time: Long = 0L,
    val attachment: Attachment? = null
)
