package desenv.danilo.simpleinsta.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.simpleinsta.R
import desenv.danilo.simpleinsta.databinding.ActivityProfileBinding
import desenv.danilo.simpleinsta.presentation.ProfileViewModel
import desenv.danilo.simpleinsta.presentation.ProfileVmFactory
import desenv.danilo.simpleinsta.presentation.state.StateView
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class ProfileActivity : BaseActivity() {

    @Inject
    lateinit var mProfileVmFactory: ProfileVmFactory

    val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(
            this,
            mProfileVmFactory
        )
            .get(ProfileViewModel::class.java)
    }
    lateinit var binding: ActivityProfileBinding


    private val MENU_ITEM_ITEM1 = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        toolbar.title = "Profile"
        initSubscribes()
    }


    private fun initSubscribes() {

        viewModel.getStateView()
            .autoDisposable(scopeProvider)
            .subscribe {
                when (it.state) {
                    StateView.Status.ERROR -> rootProfile.snackbar(it.idMessage).show()
                }
            }

        viewModel
            .getLogoutSucess()
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

