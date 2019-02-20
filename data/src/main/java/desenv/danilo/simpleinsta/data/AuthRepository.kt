package desenv.danilo.simpleinsta.data

import android.content.Context
import io.reactivex.Completable

interface AuthRepository {
    fun registerToken(context: Context, token: String): Completable
}