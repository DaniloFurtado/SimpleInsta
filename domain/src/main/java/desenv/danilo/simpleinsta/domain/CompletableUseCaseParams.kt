package desenv.danilo.simpleinsta.domain

import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CompletableUseCaseParams<in Param1, in Param2>(
    private val schedulerProvider: BaseSchedulerProvider
) {

    private val disposables =  CompositeDisposable()

    abstract fun buildUseCaseCompletable(param1: Param1? = null, param2: Param2? = null): Completable

    open fun execute(
        param1: Param1? = null,
        param2: Param2? = null,
        complete: (() -> Unit),
        onError: (e: Throwable) -> Unit)
    {
        val single = this.buildUseCaseCompletable(param1, param2)
            .subscribeOn(schedulerProvider.schedulerExecutor)
            .observeOn(schedulerProvider.schedulerPost)
        dispose()
        addDisposable(single.subscribe(
            {
                complete.invoke()
            },
            { error ->
                  onError.invoke(error)
            }
        ))
    }

    fun dispose() {
        disposables.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}