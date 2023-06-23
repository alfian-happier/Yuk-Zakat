package app.alfian.yukzakat.util

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@JvmName("addToComposite")
fun Disposable.addTo(disposableComposite: CompositeDisposable) {
    disposableComposite.add(this)
}

fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}