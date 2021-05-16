package you.village.util

import you.village.activity.login.model.User
import you.village.activity.main.home.model.Item
import you.village.viewmodel.MainViewModel

object Database {
    private val vm = MainViewModel.instance

    fun login(id: String, action: (User?) -> Unit) {
        vm.firestore
            .collection("users")
            .document(id)
            .get()
            .addOnSuccessListener { user ->
                action(user.toObject(User::class.java))
            }
    }

    fun <T> upload(value: T) {
        when (value) {
            is Item -> {
                vm.firestore.collection("items").document(value.uuid).set(value)
                vm.addItem(value)
            }
            is User -> {
                vm.firestore.collection("users").document(value.id).set(value)
                vm.addUser(value)
            }
        }
    }

    fun <T> remove(value: T) {
        when (value) {
            is Item -> {
                vm.firestore.collection("items").document(value.uuid).delete()
                vm.removeItem(value)
            }
        }
    }

    fun init() {
        vm.firestore.collection("users").get().addOnSuccessListener {
            it.toObjects(User::class.java).forEach { user ->
                vm.addUser(user)
            }
        }

        loadItems()
    }

    fun requestImageUrls(uuid: String) {
        if (vm.getImageUrlsFromUuid(uuid).value!!.isEmpty()) {
            vm.storage.reference.child("items/$uuid").listAll().addOnSuccessListener {
                it.items.forEach { task ->
                    task.downloadUrl.addOnSuccessListener { uri ->
                        vm.addDownloadUrlToUuid(uuid, uri.toString())
                    }
                }
            }
        }
    }

    fun loadItems() {
        vm.firestore.collection("items").get().addOnSuccessListener {
            it.toObjects(Item::class.java).forEach { item ->
                vm.addItem(item)
            }
        }
    }
}
