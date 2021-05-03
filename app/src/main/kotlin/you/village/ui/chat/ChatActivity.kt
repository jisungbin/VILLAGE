package you.village.ui.chat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.Date
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.ui.BaseActivity
import you.village.ui.chat.model.Chat

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class ChatActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setStatusBarColor(colors.primary)

        setContent {
            MaterialBind {
                ChatBind()
            }
        }
    }

    @Composable
    private fun ChatBind() {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = colors.primary,
                    title = {
                        Text("${if (vm.item.owner.id == vm.me.id) vm.target.name else vm.me.name} 님과의 채팅")
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { finish() }
                        )
                    }
                )
            }
        ) {
            val messageField = remember { mutableStateOf(TextFieldValue()) }
            val chatItems = mutableStateListOf<Chat>()

            database
                .getReference("messages/${vm.item.id}")
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        chatItems.add(snapshot.getValue(Chat::class.java)!!)
                    }

                    override fun onChildChanged(
                        snapshot: DataSnapshot,
                        previousChildName: String?,
                    ) {
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {}
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                })

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp)
                ) {
                    items(
                        items = chatItems.toList(),
                        itemContent = { ChatBubbleBind(chat = it, me = vm.me) }
                    )
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AttachFile,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 60.dp, end = 60.dp)
                    ) {
                        TextField(
                            value = messageField.value,
                            onValueChange = { messageField.value = it },
                            placeholder = { Text("메시지 입력") },
                            modifier = Modifier.fillMaxSize(),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                backgroundColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    val chat = Chat(
                                        id = vm.item.id,
                                        target = vm.target,
                                        owner = vm.me,
                                        message = messageField.value.text,
                                        time = Date().time,
                                        attachment = null
                                    )
                                    database
                                        .getReference("messages/${vm.item.id}")
                                        .push()
                                        .setValue(chat)
                                    messageField.value = TextFieldValue()
                                }
                        )
                    }
                }
            }
        }
    }
}
