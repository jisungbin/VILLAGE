package you.village.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import you.village.R
import you.village.theme.MaterialBind
import you.village.theme.typography
import you.village.util.doDelay

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
        var inputId by remember { mutableStateOf(TextFieldValue()) }
        var inputPassword by remember { mutableStateOf(TextFieldValue()) }

        doDelay(2000) { showLoginArea = true }

        Box {
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "ⓒ VILLAGE 2021. All rights reserved.", fontSize = 10.sp)
            }
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(R.mipmap.ic_launcher), contentDescription = null)
                Text(text = stringResource(R.string.app_name), style = typography.h1)
                AnimatedVisibility(visible = showLoginArea,
                    modifier = Modifier.padding(top = 20.dp)) {
                    Column {
                        TextField(value = inputId,
                            onValueChange = { inputId = it },
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .border(1.dp, Color.Black),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        TextField(value = inputPassword,
                            onValueChange = { inputPassword = it },
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .border(1.dp, Color.Black)
                                .padding(top = 8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            )
                        )
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.Black)) {
                            Text(text = "Login In", style = typography.button, color = Color.White)
                        }
                        Text(text = "회원가입하기", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}