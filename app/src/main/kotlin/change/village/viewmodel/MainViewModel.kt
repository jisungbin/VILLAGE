package change.village.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import change.village.activity.login.model.User
import change.village.activity.main.home.model.Item
import java.util.*

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class MainViewModel private constructor() : ViewModel() {
    lateinit var me: User
    private val _users: MutableList<User> = mutableListOf()
    private val _imageUrls: HashMap<String, MutableLiveData<SnapshotStateList<String>>> =
        hashMapOf()
    private val _items = MutableLiveData<SnapshotStateList<Item>>().apply {
        value = SnapshotStateList()
    }
    val firestore = Firebase.firestore
    val database = Firebase.database
    val storage = Firebase.storage
    val auth = Firebase.auth.apply {
        languageCode = "ko"
    }

    val users: List<User> get() = _users
    val items: LiveData<SnapshotStateList<Item>> get() = _items

    fun getItemFromUuid(uuid: String) = items.value!!.find { it.uuid == uuid }!!

    fun getUserFromUuid(uuid: String) = users.find { it.uuid == uuid }!!

    private fun getDownloadUrlsFromUuidAsMutable(uuid: String): MutableLiveData<SnapshotStateList<String>> {
        if (_imageUrls[uuid] == null) {
            _imageUrls[uuid] = MutableLiveData(SnapshotStateList())
        }
        return _imageUrls[uuid]!!
    }

    fun getImageUrlsFromUuid(uuid: String): LiveData<SnapshotStateList<String>> {
        if (_imageUrls[uuid] == null) {
            _imageUrls[uuid] = MutableLiveData(SnapshotStateList())
        }
        return _imageUrls[uuid]!!
    }

    fun addDownloadUrlToUuid(uuid: String, url: String) {
        val value = getImageUrlsFromUuid(uuid)
        value.value!!.removeIf { it == url }
        value.value!!.add(url)
        getDownloadUrlsFromUuidAsMutable(uuid).postValue(value.value!!)
    }

    fun addUser(user: User) {
        _users.removeIf { it.uuid == user.uuid }
        _users.add(user)
    }

    fun addItem(item: Item) {
        val value = _items.value ?: mutableStateListOf()
        value.removeIf { it.uuid == item.uuid }
        value.add(item)
        _items.postValue(value)
    }

    fun removeItem(item: Item) {
        val value = _items.value ?: mutableStateListOf()
        value.removeIf { it.uuid == item.uuid }
        _items.postValue(value)
    }

    companion object {
        val instance by lazy { MainViewModel() }
    }
}
