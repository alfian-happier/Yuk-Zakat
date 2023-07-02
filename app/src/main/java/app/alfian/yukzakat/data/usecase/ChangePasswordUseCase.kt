package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.repo.UserRepo
import io.reactivex.Observable

/**
 * Created by Alfian on 6/22/2023.
 */
class ChangePasswordUseCase (private val userRepo: UserRepo) {

    sealed class ChangePasswordResult {
        data class Success(val response : String) : ChangePasswordResult()
        data class Failure(val error : Exception) : ChangePasswordResult()
    }

    fun execute(currentPassword: String, newPassword: String) : Observable<ChangePasswordResult> {
        return userRepo.changePassword(currentPassword,newPassword)
            .map { ChangePasswordResult.Success(it) as ChangePasswordResult }
            .onErrorReturn { ChangePasswordResult.Failure(it as Exception) }
    }
}