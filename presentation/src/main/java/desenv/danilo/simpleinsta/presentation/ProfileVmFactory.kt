package desenv.danilo.simpleinsta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.simpleinsta.dataapi.ApiUserRepository
import desenv.danilo.simpleinsta.dataapi.api.APIClient
import desenv.danilo.simpleinsta.dataapi.api.ApiUser

import desenv.danilo.simpleinsta.domain.interactor.user.ListUserPostsUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.LogoutUserUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.ViewUserDetailUseCase
import desenv.danilo.simpleinsta.presentation.executor.SchedulerProvider

class ProfileVmFactory : ViewModelProvider.NewInstanceFactory() {
    // TODO replace this class by DI with Koin
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiUser = APIClient.getAPIService().create(ApiUser::class.java)
        val repo = ApiUserRepository(apiUser)
        return when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(
                    ListUserPostsUseCase(repo, SchedulerProvider()),
                    LogoutUserUseCase(repo, SchedulerProvider()),
                    ViewUserDetailUseCase(repo, SchedulerProvider())
                ) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}