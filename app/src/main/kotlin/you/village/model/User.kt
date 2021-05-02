package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class User(
    val id: String,
    var name: String,
    var locate: String,
    var profileImageUrl: String,
    var likeItem: List<Int>,
    var wrotePost: List<Int>,
    var uploadItem: List<Int>,
)
