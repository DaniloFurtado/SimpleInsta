package desenv.danilo.simpleinsta.ui.auth

import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationRepository
import io.reactivex.Single

class AuthenticationRepositoryMock: AuthenticationRepository {

    var responseLogout = true
    var stringToken = "1234567890"
    var exception: Exception? = null

    override fun extractToken(url: String): Single<String> {
        return if (exception == null)
            Single.just(stringToken)
        else
            Single.error(exception)
    }

    override fun registerToken(token: String): Single<Boolean> {
        return if (exception == null)
            Single.just(responseLogout)
        else
            Single.error(exception)
    }
}