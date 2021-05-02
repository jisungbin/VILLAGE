package you.village.model

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val id: Int,
    val name: String,
    val likeCount: Int,
    val price: Int, // 1회 대여값
    val rentLength: Int, // 1회 대여 개월
    val discountPercentage: Int,
    val owner: User,
    val uploadDate: Long,
)
