package desenv.danilo.simpleinsta.presentation

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import desenv.danilo.simpleinsta.data.model.ParamRegisterToken
import desenv.danilo.simpleinsta.data.util.Constants
import desenv.danilo.simpleinsta.domain.interactor.auth.RegisterTokenUseCase
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.presentation.state.StateView
import io.reactivex.subjects.PublishSubject

class AuthViewModel(
    private val registerTokenUseCase: RegisterTokenUseCase
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


    fun regiterToken(context: Context, urlToken: String) {
        val paramRegisterToken = ParamRegisterToken(context, urlToken)
        registerTokenUseCase.execute(
            paramRegisterToken,
            onSuccess = { token ->
                ApplicationApp.mToken = token
                stateAuth.onNext(StateView(StateView.Status.SUCCESS))
            },
            onError = {
                showProgress.set(false)
                showButtonLogin.set(true)
                stateAuth.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error))
            },
            doOnSubscribe = {
                showProgress.set(true)
            },
            doOnTerminate = {
                showProgress.set(false)
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
        registerTokenUseCase.dispose()
    }
}