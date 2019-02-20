package desenv.danilo.simpleinsta.domain.interactor.user

import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.domain.CompletableUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Completable

class LogoutUserUseCase(
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
): CompletableUseCase<Unit>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: Unit?): Completable {
        return repository.logout()
    }
}