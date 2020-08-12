package desenv.danilo.simpleinsta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.simpleinsta.presentation.di.DaggerPresentationComponent

class AuthVmFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                DaggerPresentationComponent.builder().build().initAuthViewModel() as T

             else -> throw IllegalArgumentException("ViewModel Not Found")
        }

    }

}