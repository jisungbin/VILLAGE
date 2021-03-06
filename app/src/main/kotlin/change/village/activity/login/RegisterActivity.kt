package change.village.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import change.village.R
import change.village.theme.MaterialBind
import change.village.theme.typography
import change.village.ui.RoundedTextField
import change.village.ui.VerticalSpace
import change.village.util.IntentKey
import change.village.util.fontResource
import change.village.util.toast

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
        val nameField = remember { mutableStateOf(TextFieldValue()) }
        val idField = remember { mutableStateOf(TextFieldValue()) }
        val passwordField = remember { mutableStateOf(TextFieldValue()) }
        val mainEmailField = remember { mutableStateOf(TextFieldValue()) }
        val subEmailField = remember { mutableStateOf(TextFieldValue()) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AppRegistration,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "???????????? ??????",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
                VerticalSpace(30.dp)
                TextField(label = "??????", value = nameField)
                TextField(label = "?????????", value = idField)
                TextField(
                    label = "????????????",
                    value = passwordField,
                    keyboardType = KeyboardType.Password
                )
                EmailTextField(mainValue = mainEmailField, subValue = subEmailField)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {
                        val name = nameField.value.text
                        val id = idField.value.text
                        val password = passwordField.value.text
                        val email = "${mainEmailField.value.text}@${subEmailField.value.text}"

                        if (
                            name.isNotBlank() && id.isNotBlank() &&
                            password.isNotBlank() && mainEmailField.value.text.isNotBlank() &&
                            subEmailField.value.text.isNotBlank()
                        ) {
                            if (id.length > 7 && password.length > 7) {
                                val intent = Intent(
                                    this@RegisterActivity,
                                    PhoneNumberVerifyActivity::class.java
                                ).apply {
                                    putExtra(IntentKey.Name, name)
                                    putExtra(IntentKey.Id, id)
                                    putExtra(IntentKey.Password, password)
                                    putExtra(IntentKey.Email, email)
                                }
                                startActivity(intent)
                                finish()
                            } else {
                                toast("???????????? ??????????????? ?????? 8?????? ???????????? ????????? ?????????.")
                            }
                        } else {
                            toast("?????? ????????? ?????????.")
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(
                        text = "Next",
                        style = typography.button,
                        color = Color.White,
                        fontFamily = fontResource(font = R.font.righteous)
                    )
                }
            }
        }
    }

    @Composable
    private fun TextField(
        label: String,
        value: MutableState<TextFieldValue>,
        keyboardType: KeyboardType = KeyboardType.Text,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(label)
            RoundedTextField(
                value = value,
                modifier = Modifier.width(200.dp),
                keyboardType = keyboardType
            )
        }
    }

    @Composable
    private fun EmailTextField(
        mainValue: MutableState<TextFieldValue>,
        subValue: MutableState<TextFieldValue>,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("?????????")
            Row(
                modifier = Modifier.width(200.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                RoundedTextField(
                    value = mainValue,
                    modifier = Modifier.width(100.dp)
                )
                Text(text = "@", modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                RoundedTextField(
                    value = subValue,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
