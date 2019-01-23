package desenv.danilo.simpleinsta.data.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.simpleinsta.data.application.ApplicationApp
import desenv.danilo.simpleinsta.data.injection.Inject
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationActivity
import desenv.danilo.simpleinsta.data.util.Constants
import desenv.danilo.simpleinsta.databinding.ActivityProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.design.snackbar

class ProfileActivity : AppCompatActivity() {

    lateinit var viewModel: ProfileViewModel
    lateinit var binding: ActivityProfileBinding
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }
    private val MENU_ITEM_ITEM1 = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Inject.inject(this)
        setSupportActionBar(toolbar)
        toolbar.title = "Profile"
        lifecycle.addObserver(viewModel)
        initSubscribes()
    }


    private fun initSubscribes() {
        viewModel.actionError
            .autoDisposable(scopeProvider)
            .subscribe {
                rootProfile.snackbar(it).show()
            }

        viewModel
            .logoutSucess
            .autoDisposable(scopeProvider)
            .subscribe {
                startActivity(Intent(this, AuthenticationActivity::class.java))
                this.finish()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, "Logout")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            MENU_ITEM_ITEM1 -> {
                viewModel.logout()
                true
            }
            else -> false
        }
    }
}

