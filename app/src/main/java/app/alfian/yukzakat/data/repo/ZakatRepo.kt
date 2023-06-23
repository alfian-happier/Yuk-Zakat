package app.alfian.yukzakat.data.repo

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.model.Zakat
import io.reactivex.Observable

/**
 * Created by Zharfan on 6/13/2023.
 */

interface ZakatRepo {
    fun payZakat(user: User,zakat: Zakat) : Observable<String>
    fun getZakatHistory(user: User)  : Observable<List<Zakat>>
}