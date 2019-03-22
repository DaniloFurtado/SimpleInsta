package desenv.danilo.simpleinsta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.simpleinsta.presentation.di.DaggerPresentationComponent

class ProfileVmFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                DaggerPresentationComponent.builder().build().initProfileViewModel() as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}