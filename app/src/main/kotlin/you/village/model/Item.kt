package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val id: String = "",
    var name: String = "",
    var description: String = "",
    var likeCount: Int = 0,
    var price: Long = 0L, // 1회 대여값
    var rentLength: Int = 0, // 1회 대여 개월
    var discountPercentage: Int = 0,
    var itemScope: Int = 0, // 아이템 노출 범위
    val owner: User = User(),
    val uploadDate: Long = 0L,
)
