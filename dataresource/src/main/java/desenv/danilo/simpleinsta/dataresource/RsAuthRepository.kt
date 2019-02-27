package desenv.danilo.simpleinsta.dataresource

import android.annotation.SuppressLint
import android.content.Context
import desenv.danilo.simpleinsta.data.AuthRepository
import desenv.danilo.simpleinsta.data.util.Constants
import io.reactivex.Single

class RsAuthRepository: AuthRepository {
    @SuppressLint("ApplySharedPref")
    override fun registerToken(context: Context, token: String): Single<String> {
        return Single.create<String> {
            try {
                val pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                val edit = pref!!.edit()
                edit.putString("token", token)
                edit.commit()
                it.onSuccess(token)
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }
}