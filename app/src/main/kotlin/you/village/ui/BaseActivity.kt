package you.village.ui

import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import you.village.model.Item
import you.village.viewmodel.MainViewModel

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

open class BaseActivity : ComponentActivity() {
    val vm = MainViewModel.instance()
    val firestore = Firebase.firestore
    val storage = Firebase.storage
    val auth = Firebase.auth.apply {
        languageCode = "ko"
    }
}
