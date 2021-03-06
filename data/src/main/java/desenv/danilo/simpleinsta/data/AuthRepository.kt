package desenv.danilo.simpleinsta.data

import android.content.Context
import io.reactivex.Single

interface AuthRepository {
    fun registerToken(context: Context, token: String): Single<String>
}