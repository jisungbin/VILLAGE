package you.village

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import you.village.activity.login.model.User
import you.village.activity.main.home.model.Item

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainViewModel private constructor() : ViewModel() {
    lateinit var me: User
    private val _users: MutableList<User> = mutableListOf()
    private val _items: MutableList<Item> = mutableListOf()
    val firestore = Firebase.firestore
    val database = Firebase.database
    val storage = Firebase.storage
    val auth = Firebase.auth.apply {
        languageCode = "ko"
    }

    val users get() = _users.toList()
    val items get() = _items.toList()

    fun getItemFromId(id: String) = items.find { it.id == id }!!
    fun getUserFromId(id: String) = users.find { it.id == id }!!

    fun addUser(user: User) {
        _users.add(user)
    }

    fun addItem(item: Item) {
        _items.add(item)
    }

    companion object {
        val instance by lazy { MainViewModel() }
    }
}
