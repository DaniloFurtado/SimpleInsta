package desenv.danilo.simpleinsta.data.application

import android.app.Application
import android.content.Context
import desenv.danilo.simpleinsta.data.util.Constants

class ApplicationApp: Application() {

    companion object {
        var mSelf: ApplicationApp? = null
        var mToken: String? = ""
    }


    override fun onCreate() {
        super.onCreate()
        mSelf = this

        val pref = ApplicationApp.mSelf?.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        mToken = pref?.getString("token", "")
    }

}