package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class User(
    val id: String,
    val name: String,
    val profileImageUrl: String,
    val likeItem: List<Int>,
    val wrotePost: List<Int>,
    val uploadItem: List<Int>,
)
