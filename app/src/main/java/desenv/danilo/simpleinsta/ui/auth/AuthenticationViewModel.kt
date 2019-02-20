package desenv.danilo.simpleinsta.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import desenv.danilo.simpleinsta.util.BaseSchedulerProvider
import desenv.danilo.simpleinsta.util.Constants
import desenv.danilo.simpleinsta.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class AuthenticationViewModel(private val repositoryImp: AuthenticationRepository
                              ,private val schedulerProvider: BaseSchedulerProvider): ViewModel(), LifecycleObserver {
    private val disposable = CompositeDisposable()
    val publishError = PublishSubject.create<String>()
    val authenticatedSucess = PublishSubject.create<Boolean>()
    val loadInstaPage = PublishSubject.create<String>()
    val showButtonLogin = ObservableField<Boolean>(true)
    val showProgress    = ObservableField<Boolean>(false)

    fun registerToken(url: String){
        disposable.add(
            repositoryImp
                .extractToken(url)
                .doOnSubscribe { showProgress.set(true) }
                .subscribeOn(schedulerProvider.io())
                .flatMap{ token ->
                    repositoryImp.registerToken(token)
                }
                .observeOn(schedulerProvider.mainThread())
                .subscribe ({
                    if (it) {
                        showProgress.set(false)
                        authenticatedSucess.onNext(true)
                    }else{
                        showProgress.set(false)
                        showButtonLogin.set(true)
                        publishError.onNext("Error fetching the token")
                    }
                },{
                    showProgress.set(false)
                    showButtonLogin.set(true)
                    publishError.onNext(it.message ?: "Error taken token")
                }))
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
    private fun onDestroy(){
        disposable.clear()
    }

    class Factory(private val authenticationRepositoryImp: AuthenticationRepositoryImp)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = AuthenticationViewModel(authenticationRepositoryImp, SchedulerProvider()) as T
    }
}