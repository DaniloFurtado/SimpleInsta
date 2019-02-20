package desenv.danilo.simpleinsta.data

import android.content.Context
import desenv.danilo.simpleinsta.data.model.ApiResponse
import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun logout(context: Context): Completable

    fun getUserPosts(accessToken: String): Single<ApiResponse<Array<DataMedias>>>

    fun getUserData(accessToken: String): Single<ApiResponse<User>>
}