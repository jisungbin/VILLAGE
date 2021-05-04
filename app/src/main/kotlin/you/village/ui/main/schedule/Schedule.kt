package you.village.ui.main.schedule

import you.village.model.Item
import you.village.model.User

data class Schedule(
    val id: String = "",
    val owner: User = User(),
    val target: User = User(),
    val name: String = "",
    val item: Item = Item()
)
