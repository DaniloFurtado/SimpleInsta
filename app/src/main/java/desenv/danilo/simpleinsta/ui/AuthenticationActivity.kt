package desenv.danilo.simpleinsta.ui

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.simpleinsta.R
import desenv.danilo.simpleinsta.databinding.ActivityAuthDialogBinding
import desenv.danilo.simpleinsta.presentation.AuthViewModel
import desenv.danilo.simpleinsta.presentation.AuthVmFactory
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.presentation.state.StateView
import kotlinx.android.synthetic.main.activity_auth_dialog.*
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class AuthenticationActivity : BaseActivity() {

    @Inject
    lateinit var mAuthVmFactory: AuthVmFactory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProviders.of(
            this,
            mAuthVmFactory
        )
            .get(AuthViewModel::class.java)
    }
    lateinit var binding: ActivityAuthDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_dialog)
        binding.viewModelAuth = viewModel
        lifecycle.addObserver(viewModel)
        initializeWebView()
        initSubscribers()
    }

    private fun initSubscribers() {
        viewModel.getLoadInstaPage()
            .autoDisposable(scopeProvider)
            .subscribe {
                webViewAuth.loadUrl(it)
            }

        viewModel.getStateAuth()
            .autoDisposable(scopeProvider)
            .subscribe {
                when (it.state) {
                    StateView.Status.SUCCESS -> {
                        startProfile()
                    }
                    StateView.Status.ERROR -> {
                        rootAuthLayout.snackbar(it.idMessage).show()
                    }
                    StateView.Status.LOADING -> {
                    }
                }

            }
    }

    private fun initializeWebView() {
        webViewAuth.settings.javaScriptEnabled = true
        webViewAuth.settings.setSupportZoom(true)
        webViewAuth.settings.domStorageEnabled = true
        val webClient = object: WebViewClient(){
            var authCompleted = false

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!authCompleted && url != null){
                    authCompleted = true
                    viewModel.regiterToken(ApplicationApp.mSelf!!.applicationContext, url)
                }
            }
        }
        webViewAuth.webViewClient = webClient
    }

    private fun startProfile(){
        startActivity(Intent(this, ProfileActivity::class.java))
        this.finish()
    }
}
