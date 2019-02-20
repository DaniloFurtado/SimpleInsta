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
        onNext: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
        onComplete: (() -> Unit)? = null)
    {
        val single = this.buildUseCaseSingle(params)
            .subscribeOn(schedulerProvider.schedulerExecutor)
            .observeOn(schedulerProvider.schedulerPost)
        dispose()
        addDisposable(single.subscribe(
            { t: T ->
                onNext.invoke(t)
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