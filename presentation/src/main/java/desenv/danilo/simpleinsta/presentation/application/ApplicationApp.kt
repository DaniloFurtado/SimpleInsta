package desenv.danilo.simpleinsta.presentation.application

import android.app.Application
import android.content.Context

class ApplicationApp: Application() {

    companion object {
        var mSelf: ApplicationApp? = null
        var mToken: String? = ""
    }


    override fun onCreate() {
        super.onCreate()
        ApplicationApp.mSelf = this

        val pref = ApplicationApp.mSelf?.getSharedPreferences("pref_app_insta", Context.MODE_PRIVATE)
        ApplicationApp.mToken = pref?.getString("token", "")
    }

}