package desenv.danilo.simpleinsta.domain.interactor.user

import android.accounts.NetworkErrorException
import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.domain.SingleUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Single

class ListUserPostsUseCase(
    private val repository: UserRepository,
    schedulerProvider: BaseSchedulerProvider
): SingleUseCase<Array<DataMedias>, String>(schedulerProvider) {
    override fun buildUseCaseSingle(params: String?): Single<Array<DataMedias>> {
        return if (params != null && params.isNotEmpty())
            repository.getUserPosts(params)
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