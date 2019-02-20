package desenv.danilo.simpleinsta.presentation

import androidx.lifecycle.*
import desenv.danilo.simpleinsta.domain.interactor.user.ListUserPostsUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.LogoutUserUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.ViewUserDetailUseCase

class ProfileViewModel(
    private val listUserPostsUseCase: ListUserPostsUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val viewUserDetailUseCase: ViewUserDetailUseCase
): ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){

    }




    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        listUserPostsUseCase.dispose()
        logoutUserUseCase.dispose()
        viewUserDetailUseCase.dispose()
    }


}