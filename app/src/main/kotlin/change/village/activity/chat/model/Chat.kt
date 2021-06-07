package change.village.activity.chat.model

data class Chat(
    val uuid: String = "",
    val targetUuid: String = "",
    val ownerUuid: String = "",
    val message: String = "",
    val time: Long = 0L,
    val attachment: Attachment? = null
)
