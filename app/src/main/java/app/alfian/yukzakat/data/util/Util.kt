package app.alfian.yukzakat.data.util

import app.alfian.yukzakat.data.model.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

/**
 * Created by Alfian on 6/13/2023.
 */

fun QueryDocumentSnapshot.toUser() : User {
    val user = User(
        id = this.id,
        username = this.get("username") as String,
        fullname = this.get("fullname") as String,
        email = this.get("email") as String)
    (this.get("display_pict_base64") as String).let { displayPictBase64 ->
        if (displayPictBase64.isNotBlank())
            user.displayPictBase64 = displayPictBase64
    }
    return user
}

fun DocumentSnapshot.toUser() : User {
    val user = User(
        id = this.id,
        username = this.get("username") as String,
        fullname = this.get("fullname") as String,
        email = this.get("email") as String)
    (this.get("display_pict_base64") as String).let { displayPictBase64 ->
        if (displayPictBase64.isNotBlank())
            user.displayPictBase64 = displayPictBase64
    }
    return user
}