package you.village.activity.main.schedule.model

import you.village.activity.login.model.User
import you.village.activity.main.home.model.Item

data class Schedule(
    val uuid: String = "",
    val owner: User = User(),
    val target: User = User(),
    val name: String = "",
    val item: Item = Item()
)
