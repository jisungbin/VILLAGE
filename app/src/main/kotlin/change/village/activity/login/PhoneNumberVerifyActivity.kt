package change.village.activity.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
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
import change.village.activity.login.model.User
import change.village.activity.main.MainActivity
import change.village.theme.MaterialBind
import change.village.theme.typography
import change.village.ui.RoundedTextField
import change.village.ui.VerticalSpace
import change.village.util.*
import change.village.viewmodel.MainViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

/**
 * Created by SungBin on 2021-05-02.
 */

class PhoneNumberVerifyActivity : ComponentActivity() {

    private val vm = MainViewModel.instance
    private lateinit var name: String
    private lateinit var id: String
    private lateinit var password: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.run {
            name = getStringExtra(IntentKey.Name)!!
            id = getStringExtra(IntentKey.Id)!!
            password = getStringExtra(IntentKey.Password)!!
            email = getStringExtra(IntentKey.Email)!!
        }

        setContent {
            MaterialBind {
                VerifyBind()
            }
        }
    }

    @Composable
    private fun VerifyBind() {
        var isCodeVerified = false
        var codeVerifyId = ""
        val phoneNumberField = remember { mutableStateOf(TextFieldValue()) }
        val verifyCodeField = remember { mutableStateOf(TextFieldValue()) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "????????? ?????? ????????????",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
                VerticalSpace(30.dp)
                Text(text = "????????? ?????? ??????")
                VerticalSpace(8.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedTextField(
                        value = phoneNumberField,
                        placeholder = "010........",
                        modifier = Modifier.width(170.dp),
                        keyboardType = KeyboardType.Number
                    )
                    OutlinedButton(
                        onClick = {
                            val options = PhoneAuthOptions.newBuilder(vm.auth)
                                .setPhoneNumber("+82${phoneNumberField.value.text.substring(1)}")
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(this@PhoneNumberVerifyActivity)
                                .setCallbacks(object :
                                        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                            isCodeVerified = true
                                            toast("???????????? ????????? ?????????????????????.")
                                        }

                                        override fun onVerificationFailed(exception: FirebaseException) {
                                            Log.e("VerificationFailed - Error", exception.message.toString())
                                            Log.e("VerificationFailed - PhoneNumber", phoneNumberField.value.text)
                                            toast("???????????? ????????? ??????????????????.\n\n[${exception.message}]")
                                        }

                                        override fun onCodeSent(
                                            verificationId: String,
                                            token: PhoneAuthProvider.ForceResendingToken,
                                        ) {
                                            codeVerifyId = verificationId
                                            toast("??????????????? ?????????????????????.")
                                        }
                                    })
                                .build()
                            PhoneAuthProvider.verifyPhoneNumber(options)
                        },
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "???????????? ??????",
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }
                VerticalSpace(30.dp)
                Text(text = "???????????? ??????")
                VerticalSpace(8.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RoundedTextField(
                        value = verifyCodeField,
                        placeholder = "6??????",
                        modifier = Modifier.width(170.dp),
                        keyboardType = KeyboardType.Number
                    )
                    OutlinedButton(
                        onClick = {
                            val credential = PhoneAuthProvider.getCredential(
                                codeVerifyId,
                                verifyCodeField.value.text
                            )
                            vm.auth
                                .signInWithCredential(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        isCodeVerified = true
                                        toast("???????????? ????????? ?????????????????????.")
                                    } else {
                                        toast("??????????????? ???????????? ????????????.\n?????? ????????? ?????????.")
                                    }
                                }
                        },
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "???????????? ??????",
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Button(
                    onClick = {
                        val phoneNumber = phoneNumberField.value.text

                        if (isCodeVerified) {
                            if (phoneNumber.isNotBlank()) {
                                val user = User(
                                    uuid = Util.createUuid(),
                                    id = id,
                                    password = EncryptUtil.encrypt(message = password),
                                    name = name,
                                    phoneNumber = phoneNumber.toLong(),
                                    locate = "",
                                    profileImageUrl = "",
                                    likeItem = listOf(),
                                    wrotePost = listOf(),
                                    uploadItem = listOf(),
                                    master = false
                                )
                                Database.upload(user)
                                toast("???????????????!")
                                open(MainActivity())
                                vm.me = user
                            } else {
                                toast("?????? ????????? ?????????.")
                            }
                        } else {
                            toast("???????????? ????????? ?????? ????????? ?????????.")
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
}
