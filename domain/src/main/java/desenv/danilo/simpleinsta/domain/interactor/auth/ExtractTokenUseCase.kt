package desenv.danilo.simpleinsta.domain.interactor.auth

import android.net.Uri
import desenv.danilo.simpleinsta.domain.SingleUseCase
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import io.reactivex.Single
import java.lang.Exception

class ExtractTokenUseCase(
    schedulerProvider: BaseSchedulerProvider
):SingleUseCase<String, String>(schedulerProvider) {
    override fun buildUseCaseSingle(params: String?): Single<String> {
        return if (params != null && params.isNotEmpty())
            extractToken(params)
        else
            Single.error(IllegalArgumentException("Book must not be null"))
    }

    private fun extractToken(url: String): Single<String> {
        return Single.create<String> {
            try {
                if (url.contains("access_token")) {
                    val uri = Uri.parse(url)
                    var accessToken = uri.encodedFragment ?: ""
                    accessToken = accessToken.substring(accessToken.lastIndexOf("=") + 1)
                    it.onSuccess(accessToken)
                }else{
                    it.onError(Exception("Getting error fetching acess token"))
                }
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }
}