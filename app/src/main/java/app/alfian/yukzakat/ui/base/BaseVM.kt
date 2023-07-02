package app.alfian.yukzakat.ui.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Alfian on 6/13/2023.
 */

abstract class BaseVM : ViewModel() {

    @Inject lateinit var context: Context
    @Inject lateinit var disposable : CompositeDisposable

    var onBackObservable = MutableLiveData<Boolean>()

    open fun onPressingBack(){
        onBackObservable.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}