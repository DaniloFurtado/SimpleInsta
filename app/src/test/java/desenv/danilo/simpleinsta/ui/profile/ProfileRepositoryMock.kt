package desenv.danilo.simpleinsta.ui.profile

import desenv.danilo.simpleinsta.data.data.apiclient.ApiResponse
import desenv.danilo.simpleinsta.data.data.models.DataMedias
import desenv.danilo.simpleinsta.data.data.models.User
import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepository
import io.reactivex.Single

class ProfileRepositoryMock: ProfileRepository {
    var responseLogout = true
    var exception: Exception? = null

    override fun getUserData(accessToken: String): Single<ApiResponse<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserPosts(accessToken: String): Single<ApiResponse<Array<DataMedias>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(): Single<Boolean> {
        return if (exception == null)
            Single.just(responseLogout)
        else
            Single.error(exception)
    }
}