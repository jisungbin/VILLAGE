package you.village.activity.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import you.village.R
import you.village.activity.main.MainActivity
import you.village.theme.MaterialBind
import you.village.theme.typography
import you.village.ui.RoundedTextField
import you.village.util.*
import you.village.viewmodel.MainViewModel

/**
 * Created by SungBin on 2021-05-02.
 */

@ExperimentalAnimationApi
class LoginActivity : ComponentActivity() {

    private val vm = MainViewModel.instance

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
                Text(
                    text = "모두의 대여",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
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

    private fun login(id: String, password: String) {
        Database.login(id) { user ->
            user?.run {
                if (this.password == EncryptUtil.encrypt(message = password)) {
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
