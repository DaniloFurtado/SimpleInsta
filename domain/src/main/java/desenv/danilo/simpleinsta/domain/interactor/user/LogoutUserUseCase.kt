package desenv.danilo.simpleinsta.domain.interactor.user

import android.content.Context
import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.domain.CompletableUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Completable

class LogoutUserUseCase(
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
) : CompletableUseCase<Context>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: Context?): Completable {
        return if (params != null)
            return repository.logout(params)
        else
            Completable.error(IllegalArgumentException("Params must be not null"))
    }
}