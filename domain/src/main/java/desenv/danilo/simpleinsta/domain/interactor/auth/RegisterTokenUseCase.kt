package desenv.danilo.simpleinsta.domain.interactor.auth

import android.net.Uri
import desenv.danilo.simpleinsta.data.AuthRepository
import desenv.danilo.simpleinsta.data.model.ParamRegisterToken
import desenv.danilo.simpleinsta.domain.SingleUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Single

class RegisterTokenUseCase(
    private val authRepository: AuthRepository,
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<String, ParamRegisterToken>(schedulerProvider) {
    override fun buildUseCaseSingle(params: ParamRegisterToken?): Single<String> {
        return if (params != null && params.urlToken.isNotEmpty()) {
            extractToken(params.urlToken)
                .flatMap { token ->
                    authRepository.registerToken(params.context, token)
                }
        } else
            Single.error(IllegalArgumentException("Parameter must be not null"))
    }


    private fun extractToken(url: String): Single<String> {
        return Single.create<String> {
            try {
                if (url.contains("access_token")) {
                    val uri = Uri.parse(url)
                    var accessToken = uri.encodedFragment ?: ""
                    accessToken = accessToken.substring(accessToken.lastIndexOf("=") + 1)
                    it.onSuccess(accessToken)
                } else {
                    it.onError(Exception("Getting error fetching acess urlToken"))
                }
            } catch (e: Exception) {
                it.onError(e)
            }
        }
    }

}