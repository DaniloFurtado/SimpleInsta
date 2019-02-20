package desenv.danilo.simpleinsta.domain.interactor.auth

import android.content.Context
import desenv.danilo.simpleinsta.data.AuthRepository
import desenv.danilo.simpleinsta.domain.CompletableUseCaseParams
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Completable

class RegisterTokenUseCase(
    private val authRepository: AuthRepository,
    schedulerProvider: BaseSchedulerProvider
): CompletableUseCaseParams<Context, String>(schedulerProvider) {
    override fun buildUseCaseCompletable(param1: Context?, param2: String?): Completable {
        return if (param2 != null && param2.isNotEmpty()
            && param1 != null)
            return authRepository.registerToken(param1, param2)
        else
            Completable.error(IllegalArgumentException("Parameter must be not null"))
    }

}