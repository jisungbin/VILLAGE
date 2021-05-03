package you.village.ui.login.locate

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.MapView
import you.village.R
import you.village.model.User
import you.village.theme.MaterialBind
import you.village.theme.typography
import you.village.ui.BaseActivity
import you.village.ui.main.MainActivity
import you.village.ui.widget.RoundedTextField
import you.village.ui.widget.VerticalSpace
import you.village.util.EncryptUtil
import you.village.util.Util
import you.village.util.fontResource
import you.village.util.open
import you.village.util.toast

/**
 * Created by SungBin on 2021-05-02.
 */

class LocateActivity : BaseActivity() {

    private lateinit var name: String
    private lateinit var id: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private val zipCodeField = mutableStateOf(TextFieldValue())
    private val addressField = mutableStateOf(TextFieldValue())

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.checkGpsService(this)
        Util.requestGpsPermission(this)

        intent.run {
            name = getStringExtra("name")!!
            id = getStringExtra("id")!!
            password = getStringExtra("password")!!
            email = getStringExtra("email")!!
            phoneNumber = getStringExtra("phone")!!
        }

        setContent {
            MaterialBind {
                LocateBind()
            }
        }
    }

    @Composable
    private fun LocateBind() {
        val locatePickState = remember { mutableStateOf<LocatePick?>(null) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Place,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "나의 동네 설정하기",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
                VerticalSpace(height = 30.dp)
                Crossfade(locatePickState.value) { state ->
                    when (state) {
                        null -> LocatePickerBind(locatePickState)
                        LocatePick.Input -> LocateInputBind()
                        LocatePick.Map -> LocateMapBind()
                    }
                }
            }
            if (locatePickState.value != null) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { locatePickState.value = null },
                        modifier = Modifier.padding(end = 10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                    ) {
                        Text(
                            text = "Back",
                            style = typography.button,
                            color = Color.White,
                            fontFamily = fontResource(font = R.font.righteous)
                        )
                    }
                    Button(
                        onClick = {
                            val user = User(
                                id = id,
                                password = EncryptUtil.encrypt(message = password),
                                name = name,
                                phoneNumber = phoneNumber.toLong(),
                                locate = "${addressField.value.text} (우편: ${zipCodeField.value.text})",
                                profileImageUrl = "",
                                likeItem = listOf(),
                                wrotePost = listOf(),
                                uploadItem = listOf()
                            )
                            firestore
                                .collection("users")
                                .document(id)
                                .set(user)
                            toast("환영합니다!")
                            open(MainActivity())
                            vm.me = user
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                    ) {
                        Text(
                            text = "Done",
                            style = typography.button,
                            color = Color.White,
                            fontFamily = fontResource(font = R.font.righteous)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun LocatePickerBind(locatePickState: MutableState<LocatePick?>) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .clickable { locatePickState.value = LocatePick.Input }
            ) {
                Spacer(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Cyan)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Text("주소 직접 입력")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .clickable { locatePickState.value = LocatePick.Map }
            ) {
                Spacer(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Cyan)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Text("지도로 선택")
                }
            }
        }
    }

    @Composable
    private fun LocateInputBind() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("주소 직접 입력")
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("우편번호")
                    RoundedTextField(value = zipCodeField, modifier = Modifier.width(150.dp))
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "주소 찾기",
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text("상세주소")
                    RoundedTextField(
                        value = addressField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        imageVector = Icons.Outlined.GpsFixed,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun LocateMapBind() {
        val context = LocalContext.current
        val map = remember { MapView(context) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("지도로 선택하기")
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .size(250.dp)
                        .background(Color.Cyan)
                )
            }
        }
    }
}
