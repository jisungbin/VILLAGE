package you.village.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import you.village.theme.MaterialBind
import you.village.ui.widget.HeightSpace
import you.village.ui.widget.RoundedTextField

/**
 * Created by SungBin on 2021-05-02.
 */

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialBind {
                RegisterBind()
            }
        }
    }

    @Composable
    private fun RegisterBind() {
        val name = remember { mutableStateOf(TextFieldValue()) }
        val id = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.AppRegistration,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "회원가입 하기",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
            HeightSpace(30.dp)
            TextField(label = "이름", value = name)
            TextField(label = "아이디", value = id)
            TextField(label = "비밀번호", value = password)
            TextField(label = "이메일", value = email)
        }
    }

    @Composable
    private fun TextField(
        label: String,
        value: MutableState<TextFieldValue>,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(label)
            RoundedTextField(
                value = value,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(200.dp)
            )
        }
    }
}
