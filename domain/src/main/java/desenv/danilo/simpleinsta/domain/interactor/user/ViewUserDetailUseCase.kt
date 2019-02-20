package desenv.danilo.simpleinsta.domain.interactor.user

import android.accounts.NetworkErrorException
import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.data.model.User
import desenv.danilo.simpleinsta.domain.SingleUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Single

class ViewUserDetailUseCase(
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
): SingleUseCase<User, String>(schedulerProvider) {
    override fun buildUseCaseSingle(params: String?): Single<User> {
        return if (params != null && params.isNotEmpty())
        repository.getUserData(params)
            .map {
                if (it.meta.code == 200)
                    it.data
                else
                    error(NetworkErrorException(it.meta.errorMessage))
            }
        else
            Single.error(IllegalArgumentException("Parameter must not be null"))
    }

}