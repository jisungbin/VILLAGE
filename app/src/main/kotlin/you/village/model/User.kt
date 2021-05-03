package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class User(
    val id: String = "",
    var password: String = "",
    var name: String = "",
    var phoneNumber: Long = 0L,
    var locate: String = "",
    var profileImageUrl: String = "",
    var likeItem: List<String> = listOf(),
    var wrotePost: List<String> = listOf(),
    var uploadItem: List<String> = listOf(),
)
