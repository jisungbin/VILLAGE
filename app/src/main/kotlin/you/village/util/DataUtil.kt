package you.village.util

import com.google.firebase.firestore.FirebaseFirestore
import you.village.model.Item
import you.village.model.User

object DataUtil {
    fun itemReUpload(item: Item, firestore: FirebaseFirestore) {
        firestore.collection("items").document(item.id).set(item)
    }

    fun userReUpload(user: User, firestore: FirebaseFirestore) {
        firestore.collection("users").document(user.id).set(user)
    }
}
