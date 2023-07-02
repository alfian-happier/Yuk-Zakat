package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.model.Zakat
import app.alfian.yukzakat.data.repo.ZakatRepo
import io.reactivex.Observable

/**
 * Created by Alfian on 6/22/2023.
 */

class GetZakatHistoryUseCase (private val zakatRepo: ZakatRepo) {

    sealed class GetZakatHistoryResult {
        data class Success(val response : List<Zakat>) : GetZakatHistoryResult()
        data class Failure(val error : Exception) : GetZakatHistoryResult()
    }

    fun execute(user: User) : Observable<GetZakatHistoryResult> {
        return zakatRepo.getZakatHistory(user)
            .map { GetZakatHistoryResult.Success(it) as GetZakatHistoryResult }
            .onErrorReturn { GetZakatHistoryResult.Failure(it as Exception) }
    }
}