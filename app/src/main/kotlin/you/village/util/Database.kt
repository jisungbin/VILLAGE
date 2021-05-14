package you.village.util

import you.village.MainViewModel
import you.village.activity.login.model.User
import you.village.activity.main.home.model.Item

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
            is Item -> vm.firestore.collection("items").document(value.id).set(value)
            is User -> vm.firestore.collection("users").document(value.id).set(value)
        }
    }

    fun <T> remove(value: T) {
        when (value) {
            is Item -> vm.firestore.collection("items").document(value.id).delete()
            is User -> vm.firestore.collection("users").document(value.id).delete()
        }
    }

    fun init() {
        vm.firestore.collection("users").get().addOnSuccessListener {
            it.toObjects(User::class.java).forEach { user ->
                vm.addUser(user)
            }
        }

        vm.firestore.collection("items").get().addOnSuccessListener {
            it.toObjects(Item::class.java).forEach { item ->
                vm.addItem(item)
            }
        }
    }
}
