package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.repo.UserRepo
import io.reactivex.Observable

/**
 * Created by Zharfan on 6/13/2023.
 */

class RegisterUseCase (private val userRepo: UserRepo) {

    sealed class RegisterResult {
        data class Success(val response : User) : RegisterResult()
        data class Failure(val error : Exception) : RegisterResult()
    }

    fun execute(username: String, fullname: String, email: String, password: String) : Observable<RegisterResult> {
        return userRepo.register(username,fullname,email,password)
            .map { RegisterResult.Success(it) as RegisterResult }
            .onErrorReturn { RegisterResult.Failure(it as Exception) }
    }
}