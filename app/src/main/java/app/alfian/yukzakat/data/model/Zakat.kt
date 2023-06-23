package app.alfian.yukzakat.data.model

/**
 * Created by Zharfan on 6/13/2023.
 */

data class Zakat(
    val fullName : String,
    val nominal : String,
    val phoneNumber : String,
    val bankAccountName : String,
    val transferPhotoBase64 : String,
    val date : String,
    val type : ZakatType,
    val anggota : List<Anggota> = arrayListOf()
)