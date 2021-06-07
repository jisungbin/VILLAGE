package change.village.activity.main.schedule.model

import change.village.activity.login.model.User
import change.village.activity.main.home.model.Item

data class Schedule(
    val uuid: String = "",
    val owner: User = User(),
    val target: User = User(),
    val name: String = "",
    val item: Item = Item()
)
