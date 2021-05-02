package you.village.ui.main.model

sealed class Category {
    object Hot : Category()
    object All : Category()
    object Sale : Category()
}
