package you.village.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import you.village.theme.MaterialBind


/**
 * Created by SungBin on 2021-05-02.
 */

class LocateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialBind {

            }
        }
    }
}