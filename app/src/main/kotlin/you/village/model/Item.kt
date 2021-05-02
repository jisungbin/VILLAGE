package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val id: Int,
    var name: String,
    var description: String,
    var imageUrl: List<String>,
    var likeCount: Int,
    var price: Int, // 1회 대여값
    var rentLength: Int, // 1회 대여 개월
    var discountPercentage: Int,
    var itemScope: Int, // 아이템 노출 범위
    val owner: User,
    val uploadDate: Long,
)
