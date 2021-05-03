package you.village.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainViewModel private constructor() : ViewModel() {


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
