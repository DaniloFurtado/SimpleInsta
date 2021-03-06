package desenv.danilo.simpleinsta.domain

import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CompletableUseCase<in Params>(
    private val schedulerProvider: BaseSchedulerProvider
) {

    private val disposables =  CompositeDisposable()

    abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    open fun execute(
        params: Params? = null,
        complete: (() -> Unit),
        onError: (e: Throwable) -> Unit,
        doOnSubscribe: (() -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    )

    {
        val single = this.buildUseCaseCompletable(params)
            .doOnSubscribe { doOnSubscribe?.invoke() }
            .subscribeOn(schedulerProvider.schedulerExecutor)
            .observeOn(schedulerProvider.schedulerPost)
            .doOnTerminate { doOnTerminate?.invoke() }
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