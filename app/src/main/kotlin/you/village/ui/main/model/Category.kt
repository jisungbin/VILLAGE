package you.village.ui.main.model

enum class Category {
    Hot {
        override fun toString() = "인기 대여품"
    },
    All {
        override fun toString() = "전체 대여품"
    },
    Sale {
        override fun toString() = "특가 대여품"
    }
}
