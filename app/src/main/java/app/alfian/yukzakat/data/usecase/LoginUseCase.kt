package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.repo.UserRepo
import io.reactivex.Observable

/**
 * Created by Alfian on 6/13/2023.
 */

class LoginUseCase (private val userRepo: UserRepo) {

    sealed class LoginResult {
        data class Success(val response : User) : LoginResult()
        data class Failure(val error : Exception) : LoginResult()
    }

    fun execute(username: String, password: String) : Observable<LoginResult> {
        return userRepo.login(username,password)
            .map { LoginResult.Success(it) as LoginResult }
            .onErrorReturn { LoginResult.Failure(it as Exception) }
    }
}