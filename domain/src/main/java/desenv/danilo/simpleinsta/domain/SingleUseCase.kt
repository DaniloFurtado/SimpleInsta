package desenv.danilo.simpleinsta.domain

import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class SingleUseCase<T, in Params>(
    private val schedulerProvider: BaseSchedulerProvider
) {

    private val disposables =  CompositeDisposable()

    abstract fun buildUseCaseSingle(params: Params? = null): Single<T>

    open fun execute(
        params: Params? = null,
        onSuccess: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
        doOnSubscribe: (() -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    )
    {
        val single = this.buildUseCaseSingle(params)
            .doOnSubscribe { doOnSubscribe?.invoke() }
            .subscribeOn(schedulerProvider.schedulerExecutor)
            .observeOn(schedulerProvider.schedulerPost)
            .doAfterTerminate { doOnTerminate?.invoke() }

        addDisposable(single.subscribe(
            { t: T ->
                onSuccess.invoke(t)
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