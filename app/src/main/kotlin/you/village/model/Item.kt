package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val id: Int,
    var name: String,
    var imageUrl: String,
    var likeCount: Int,
    var price: Int, // 1회 대여값
    var rentLength: Int, // 1회 대여 개월
    var discountPercentage: Int,
    val owner: User,
    val uploadDate: Long,
)
