package change.village.activity.main.home.model

import change.village.activity.main.category.Category
import java.util.*

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val uuid: String = "",
    var name: String = "",
    var description: String = "",
    var likeCount: Int = 0,
    var category: String = Category.None,
    var price: Long = 0L, // 1회 대여값
    var rentLength: Int = 0, // 1회 대여 개월
    var discountPercentage: Int = 0,
    val imageNames: List<String> = listOf(),
    val ownerUuid: String = "",
    val uploadDate: Long = Date().time
)
