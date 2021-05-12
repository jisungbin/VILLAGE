package you.village.ui.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import you.village.R
import you.village.model.User
import you.village.theme.MaterialBind
import you.village.theme.typography
import you.village.ui.BaseActivity
import you.village.ui.main.MainActivity
import you.village.ui.widget.RoundedTextField
import you.village.util.EncryptUtil
import you.village.util.doDelay
import you.village.util.fontResource
import you.village.util.open
import you.village.util.toast

/**
 * Created by SungBin on 2021-05-02.
 */

@ExperimentalAnimationApi
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialBind {
                MainBind()
            }
        }
    }

    @Composable
    private fun MainBind() {
        var showLoginArea by remember { mutableStateOf(false) }
        val idField = remember { mutableStateOf(TextFieldValue()) }
        val passwordField = remember { mutableStateOf(TextFieldValue()) }

        doDelay(2000) { showLoginArea = true }

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "ⓒ VILLAGE 2021. All rights reserved.",
                    fontSize = 10.sp,
                    fontFamily = fontResource(font = R.font.righteous)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null
                )
                AnimatedVisibility(visible = showLoginArea) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        RoundedTextField(
                            value = idField,
                            placeholder = "Enter Id"
                        )
                        RoundedTextField(
                            value = passwordField,
                            modifier = Modifier.padding(top = 16.dp),
                            placeholder = "Enter Password",
                            keyboardType = KeyboardType.Password
                        )
                        Button(
                            onClick = {
                                val id = idField.value.text
                                val password = passwordField.value.text

                                if (id.isNotBlank() && password.isNotBlank()) {
                                    login(id, password)
                                } else {
                                    toast("모두 입력해 주세요.")
                                }
                            },
                            modifier = Modifier.padding(top = 16.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                        ) {
                            Text(
                                text = "Login In",
                                style = typography.button,
                                color = Color.White,
                                fontFamily = fontResource(font = R.font.righteous)
                            )
                        }
                        Text(
                            text = "회원가입하기",
                            fontSize = 13.sp,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable {
                                    open(RegisterActivity(), false)
                                }
                        )
                    }
                }
            }
        }
    }

    private fun login(id: String, _password: String) {
        firestore
            .collection("users")
            .document(id)
            .get()
            .addOnSuccessListener { user ->
                user.toObject(User::class.java)?.run {
                    if (password == EncryptUtil.encrypt(message = _password)) {
                        open(MainActivity())
                        toast("${name}님, 환영합니다 :)")
                        vm.me = this
                    } else {
                        toast("비밀번호가 일치하지 않습니다.")
                    }
                } ?: toast("아이디가 존재하지 않습니다.")
            }
    }
}
