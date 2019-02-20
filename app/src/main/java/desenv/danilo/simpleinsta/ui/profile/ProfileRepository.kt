package desenv.danilo.simpleinsta.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.data.apiclient.ApiResponse
import desenv.danilo.simpleinsta.data.models.DataMedias
import desenv.danilo.simpleinsta.data.models.User
import desenv.danilo.simpleinsta.ui.profile.api.ApiProfile
import desenv.danilo.simpleinsta.util.Constants
import io.reactivex.Single
import java.lang.Exception


interface ProfileRepository{
    fun getUserData(accessToken: String): Single<ApiResponse<User>>
    fun getUserPosts(accessToken: String): Single<ApiResponse<Array<DataMedias>>>
    fun logout(): Single<Boolean>
}


class ProfileRepositoryImp(val apiProfile: ApiProfile): ProfileRepository{


    @SuppressLint("ApplySharedPref")
    override fun logout(): Single<Boolean> {
        return Single.create<Boolean> {
            try {
                val pref = ApplicationApp.mSelf?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                val edit = pref!!.edit()
                edit.putString("token", "")
                edit.commit()
                ApplicationApp.mToken = ""
                it.onSuccess(true)
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }

    override fun getUserPosts(accessToken: String): Single<ApiResponse<Array<DataMedias>>> {
        return apiProfile.getUserPosts(accessToken)
    }

    override fun getUserData(accessToken: String): Single<ApiResponse<User>> {
        return apiProfile.getUserData(accessToken)
    }

}









