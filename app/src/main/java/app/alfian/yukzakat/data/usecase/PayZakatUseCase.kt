package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.data.repo.ZakatRepo
import io.reactivex.Observable

/**
 * Created by Zharfan on 6/13/2023.
 */

class PayZakatUseCase (private val zakatRepo: ZakatRepo) {

    sealed class PayZakatResult {
        data class Success(val response : String) : PayZakatResult()
        data class Failure(val error : Exception) : PayZakatResult()
    }

    fun execute(user: User,zakat: Zakat) : Observable<PayZakatResult> {
        return zakatRepo.payZakat(user,zakat)
            .map { PayZakatResult.Success(it) as PayZakatResult }
            .onErrorReturn { PayZakatResult.Failure(it as Exception) }
    }
}