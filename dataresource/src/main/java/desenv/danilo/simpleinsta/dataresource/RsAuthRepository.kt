package desenv.danilo.simpleinsta.dataresource

import android.annotation.SuppressLint
import android.content.Context
import desenv.danilo.simpleinsta.data.AuthRepository
import desenv.danilo.simpleinsta.data.util.Constants
import io.reactivex.Completable

class RsAuthRepository: AuthRepository {
    @SuppressLint("ApplySharedPref")
    override fun registerToken(context: Context, token: String): Completable {
        return Completable.create{
            try {
                val pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                val edit = pref!!.edit()
                edit.putString("urlToken", token)
                edit.commit()
                it.onComplete()
            }catch (e: Exception){
                it.onError(e)
            }
        }
    }
}