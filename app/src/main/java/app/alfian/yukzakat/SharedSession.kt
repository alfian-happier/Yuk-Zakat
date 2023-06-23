package app.alfian.yukzakat

import android.content.Context
import android.content.SharedPreferences
import app.alfian.yukzakat.data.model.User
import com.google.gson.Gson

/**
 * Created by Zharfan on 6/13/2023.
 */

object SharedSession {

    private const val MODE = Context.MODE_PRIVATE
    private var preferences: SharedPreferences? = null

    private var USER = Pair("user",null)

    fun init(context: Context) {
        preferences = context.getSharedPreferences("yukzakatsubskey", MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var user : User?
        set(value) = preferences!!.edit {
            it.putString(USER.first, Gson().toJson(value))
        }
        get() {
            preferences?.getString(USER.first, USER.second).let {
                return Gson().fromJson(it, User::class.java)
            }
        }

    fun reset(){
        user = null
    }
}