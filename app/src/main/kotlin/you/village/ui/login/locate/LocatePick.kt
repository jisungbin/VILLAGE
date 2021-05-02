package you.village.ui.login.locate

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

sealed class LocatePick {
    object Input : LocatePick()
    object Map : LocatePick()
}
