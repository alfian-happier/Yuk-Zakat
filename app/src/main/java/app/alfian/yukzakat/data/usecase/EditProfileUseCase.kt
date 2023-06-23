package app.alfian.yukzakat.data.usecase

import app.alfian.yukzakat.data.model.User
import app.alfian.yukzakat.data.repo.UserRepo
import io.reactivex.Observable

/**
 * Created by Zharfan on 6/21/2023.
 */

class EditProfileUseCase (private val userRepo: UserRepo) {

    sealed class EditProfileResult {
        data class Success(val response : User) : EditProfileResult()
        data class Failure(val error : Exception) : EditProfileResult()
    }

    fun execute(fullname: String, email: String, displayPictBase64: String) : Observable<EditProfileResult> {
        return userRepo.editProfile(fullname,email,displayPictBase64)
            .map { EditProfileResult.Success(it) as EditProfileResult }
            .onErrorReturn { EditProfileResult.Failure(it as Exception) }
    }
}