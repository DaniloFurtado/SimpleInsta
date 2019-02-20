package desenv.danilo.simpleinsta.presentation

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import desenv.danilo.simpleinsta.data.util.Constants
import desenv.danilo.simpleinsta.domain.interactor.auth.ExtractTokenUseCase
import desenv.danilo.simpleinsta.domain.interactor.auth.RegisterTokenUseCase
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.presentation.state.StateView
import io.reactivex.subjects.PublishSubject

class AuthenticationViewModel(
    private val registerTokenUseCase: RegisterTokenUseCase,
    private val extractTokenUseCase: ExtractTokenUseCase
): ViewModel(), LifecycleObserver{

    private val stateAuth = PublishSubject.create<StateView<Unit>>()
    private val loadInstaPage = PublishSubject.create<String>()

    val showButtonLogin = ObservableField<Boolean>(true)
    val showProgress    = ObservableField<Boolean>(false)

    fun getStateAuth(): PublishSubject<StateView<Unit>> {
        return stateAuth
    }

    fun getLoadInstaPage(): PublishSubject<String> {
        return loadInstaPage
    }

    fun extractToken(urlToken: String){
        showProgress.set(true)
        extractTokenUseCase.execute(urlToken,
            {
                showProgress.set(false)
                regiterToken(ApplicationApp.mSelf!!.applicationContext, urlToken)
            },
            {
                showProgress.set(false)
                showButtonLogin.set(true)
                stateAuth.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error))
            })
    }


    fun regiterToken(context: Context, token: String){
        showProgress.set(true)
        registerTokenUseCase.execute(context, token,
            {
                showProgress.set(false)
                ApplicationApp.mToken = token
                stateAuth.onNext(StateView(StateView.Status.SUCCESS))
            },
            {
                showProgress.set(false)
                showButtonLogin.set(true)
                stateAuth.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error))
            })
    }

    fun startAuthenticate(){
        showButtonLogin.set(false)
        loadInstaPage.onNext(createUrlPageInsta())
    }

    private fun createUrlPageInsta()=
        Constants.BASE_URL
            .plus("oauth/authorize/?client_id=")
            .plus(Constants.INSTAGRAM_CLIENTE_ID)
            .plus("&redirect_uri=")
            .plus(Constants.REDIRECT_URI)
            .plus("&response_type=token")
            .plus("&display=touch&scope=public_content")

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        extractTokenUseCase.dispose()
        registerTokenUseCase.dispose()
    }
}