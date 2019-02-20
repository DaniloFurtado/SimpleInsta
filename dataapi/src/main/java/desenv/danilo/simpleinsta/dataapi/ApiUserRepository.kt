package desenv.danilo.simpleinsta.dataapi

import android.annotation.SuppressLint
import android.content.Context
import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.data.model.ApiResponse
import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.data.model.User
import desenv.danilo.simpleinsta.data.util.Constants
import desenv.danilo.simpleinsta.dataapi.api.ApiUser
import io.reactivex.Completable
import io.reactivex.Single

class ApiUserRepository(
    private val apiUser: ApiUser
): UserRepository {

    @SuppressLint("ApplySharedPref")
    override fun logout(context: Context): Completable {
        return Completable.create {
            try {
                val pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                val edit = pref!!.edit()
                edit.putString("token", "")
                edit.commit()
                it.onComplete()
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }

    override fun getUserPosts(accessToken: String): Single<ApiResponse<Array<DataMedias>>> {
        return apiUser.getUserPosts(accessToken)
    }

    override fun getUserData(accessToken: String): Single<ApiResponse<User>> {
        return apiUser.getUserData(accessToken)
    }
}