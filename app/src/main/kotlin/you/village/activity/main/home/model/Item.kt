package you.village.activity.main.home.model

import java.util.Date
import you.village.activity.login.model.User
import you.village.activity.main.category.Category

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

data class Item(
    val id: String = "",
    var name: String = "",
    var description: String = "",
    var likeCount: Int = 0,
    var category: String = Category.None,
    var price: Long = 0L, // 1회 대여값
    var rentLength: Int = 0, // 1회 대여 개월
    var discountPercentage: Int = 0,
    var itemScope: Int = 0, // 아이템 노출 범위
    val owner: User = User(),
    val uploadDate: Long = Date().time,
    val images: List<String> = listOf()
)
