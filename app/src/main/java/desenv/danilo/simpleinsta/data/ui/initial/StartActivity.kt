package desenv.danilo.simpleinsta.data.ui.initial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.simpleinsta.data.application.ApplicationApp
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationActivity
import desenv.danilo.simpleinsta.data.ui.profile.ProfileActivity
import desenv.danilo.simpleinsta.data.util.Constants
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

class StartActivity: AppCompatActivity() {

    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAuth()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe ({
                if(it){
                    // profile screen
                    startActivity(Intent(this, ProfileActivity::class.java))
                    this.finish()
                }else{
                    // Authenticate Screen
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    this.finish()
                }
            },{
                alert {
                    title = "Error"
                    message = "Erro checked Authenticcate"
                    okButton {dialog ->
                        dialog.dismiss()
                        finish()
                    }
                }.show()
            })
    }

    private fun checkAuth(): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            try {
                val pref = ApplicationApp.mSelf?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                emitter.onSuccess(pref?.getString("token", "")!!.isNotEmpty())
            } catch (e: Exception){
                emitter.onError(e)
            }
        }

    }

}