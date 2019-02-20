package desenv.danilo.simpleinsta.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.util.Constants
import io.reactivex.Single
import java.lang.Exception

interface AuthenticationRepository{


    fun extractToken(url: String): Single<String>


    fun registerToken(token: String): Single<Boolean>
}

open class AuthenticationRepositoryImp: AuthenticationRepository{

    override fun extractToken(url: String): Single<String> {
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

    @SuppressLint("ApplySharedPref")
    override fun registerToken(token: String): Single<Boolean> {
        return Single.create<Boolean> {
            try {
                ApplicationApp.mToken = token
                val pref = ApplicationApp.mSelf?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                val edit = pref!!.edit()
                edit.putString("token", token)
                edit.commit()
                it.onSuccess(true)
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }

}