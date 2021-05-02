package you.village.ui.main

sealed class Main {
    object Category : Main()
    object Home : Main()
    object Notification : Main()
    object Profile : Main()
}
