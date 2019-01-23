package desenv.danilo.simpleinsta.data.ui.auth

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.simpleinsta.data.injection.Inject
import desenv.danilo.simpleinsta.data.ui.profile.ProfileActivity
import desenv.danilo.simpleinsta.databinding.AuthDialogBinding
import kotlinx.android.synthetic.main.auth_dialog.*
import org.jetbrains.anko.design.snackbar

class AuthenticationActivity : AppCompatActivity() {



    lateinit var viewModel: AuthenticationViewModel
    lateinit var binding: AuthDialogBinding
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Inject.inject(this)
        lifecycle.addObserver(viewModel)
        initializeWebView()
        initSubscribers()
    }

    private fun initSubscribers() {
        viewModel
            .loadInstaPage
            .autoDisposable(scopeProvider)
            .subscribe { url ->
                webViewAuth.loadUrl(url)
            }

        viewModel
            .authenticatedSucess
            .autoDisposable(scopeProvider)
            .subscribe {
                startProfile()
            }
        viewModel
            .publishError
            .autoDisposable(scopeProvider)
            .subscribe {
                rootAuthLayout.snackbar("Getting error fetching acess token")
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
                    viewModel.registerToken(url)
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
