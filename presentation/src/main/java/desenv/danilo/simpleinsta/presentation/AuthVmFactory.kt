package desenv.danilo.simpleinsta.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.simpleinsta.dataresource.RsAuthRepository
import desenv.danilo.simpleinsta.domain.interactor.auth.ExtractTokenUseCase
import desenv.danilo.simpleinsta.domain.interactor.auth.RegisterTokenUseCase
import desenv.danilo.simpleinsta.presentation.executor.ShchedulerProvider

class AuthVmFactory: ViewModelProvider.NewInstanceFactory() {
    // TODO replace this class by DI with Koin
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = RsAuthRepository()
        return when {
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) ->
                AuthenticationViewModel(
                    RegisterTokenUseCase(repo, ShchedulerProvider()),
                    ExtractTokenUseCase(ShchedulerProvider())) as T

             else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}