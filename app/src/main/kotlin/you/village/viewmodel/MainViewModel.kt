package you.village.viewmodel

import androidx.lifecycle.ViewModel
import you.village.model.Item
import you.village.model.User

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainViewModel private constructor() : ViewModel() {
    lateinit var me: User
    lateinit var item: Item

    companion object {
        private lateinit var vm: MainViewModel

        fun instance(): MainViewModel {
            if (!::vm.isInitialized) {
                vm = MainViewModel()
            }
            return vm
        }
    }
}
