package app.alfian.yukzakat.data.model

/**
 * Created by Zharfan on 6/13/2023.
 */

data class User(
    val id : String,
    val username : String,
    val fullname : String,
    val email : String,
    var displayPictBase64 : String? = null
)