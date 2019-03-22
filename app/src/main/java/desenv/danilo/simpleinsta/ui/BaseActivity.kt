package desenv.danilo.simpleinsta.ui

import android.os.Build
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.snackbar.Snackbar
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import desenv.danilo.simpleinsta.ui.di.AppModule
import desenv.danilo.simpleinsta.ui.di.DaggerAppComponent


abstract class BaseActivity : AppCompatActivity() {

    protected val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }
    private var mSnackBar: Snackbar? = null

    private val daggerInjet = DaggerAppComponent.builder()
        .appModule(AppModule())
        .build()!!

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is ProfileActivity -> daggerInjet.init(this)
            is AuthenticationActivity -> daggerInjet.init(this)
        }
    }


    protected fun avaliableTransition(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun initToolbarBack(toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null)
            when (item.itemId) {
                android.R.id.home -> onBackPressed()

            }
        return super.onOptionsItemSelected(item)
    }

    fun addObserver(cycleObserver: LifecycleObserver) {
        lifecycle.addObserver(cycleObserver)
    }


    fun hideSnkack() {
        mSnackBar?.dismiss()
    }
}
