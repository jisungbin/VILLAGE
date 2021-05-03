package you.village.ui

import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

open class BaseActivity : ComponentActivity() {
    val firestore = Firebase.firestore
    val auth = Firebase.auth.apply {
        languageCode = "ko"
    }
}
