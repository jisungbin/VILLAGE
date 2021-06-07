package change.village.activity.main.model

sealed class Main {
    object Category : Main()
    object Home : Main()
    object Notification : Main()
    object Calendar : Main()
    object Profile : Main()
}
