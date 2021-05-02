package you.village.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import you.village.R
import you.village.theme.MaterialBind
import you.village.theme.typography
import you.village.ui.widget.RoundedTextField
import you.village.util.doDelay
import you.village.util.fontResource
import you.village.util.open

/**
 * Created by SungBin on 2021-05-02.
 */

@ExperimentalAnimationApi
class LoginActivity : ComponentActivity() {
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
        val id = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }

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
                    painter = painterResource(R.mipmap.ic_launcher),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    fontFamily = fontResource(R.font.righteous),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                AnimatedVisibility(visible = showLoginArea) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 50.dp)
                    ) {
                        RoundedTextField(
                            value = id,
                            placeholder = "Enter Id:"
                        )
                        RoundedTextField(
                            value = password,
                            modifier = Modifier.padding(top = 16.dp),
                            placeholder = "Enter Password:"
                        )
                        Button(
                            onClick = { /*TODO*/ },
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
                            modifier = Modifier.padding(top = 8.dp).clickable {
                                open(RegisterActivity())
                            }
                        )
                    }
                }
            }
        }
    }
}