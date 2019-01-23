package desenv.danilo.simpleinsta.data.injection

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import desenv.danilo.simpleinsta.R
import desenv.danilo.simpleinsta.data.data.apiclient.APIClient
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationActivity
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationRepositoryImp
import desenv.danilo.simpleinsta.data.ui.auth.AuthenticationViewModel
import desenv.danilo.simpleinsta.data.ui.profile.ProfileActivity
import desenv.danilo.simpleinsta.data.ui.profile.ProfileRepositoryImp
import desenv.danilo.simpleinsta.data.ui.profile.ProfileViewModel
import desenv.danilo.simpleinsta.data.ui.profile.api.ApiProfile


object Inject{

    fun inject(obj: Any) {
        when (obj) {
            is ProfileActivity -> {
                val api = APIClient.getAPIService().create(ApiProfile::class.java)
                obj.viewModel =  ViewModelProviders
                    .of(obj, ProfileViewModel.Factory(ProfileRepositoryImp(api)))
                    .get(ProfileViewModel::class.java)

                obj.binding = DataBindingUtil.setContentView(obj, R.layout.activity_profile)
                obj.binding.viewModel = obj.viewModel
            }
            is AuthenticationActivity ->{
                obj.viewModel = ViewModelProviders
                    .of(obj, AuthenticationViewModel.Factory(AuthenticationRepositoryImp()))
                    .get(AuthenticationViewModel::class.java)

                obj.binding = DataBindingUtil.setContentView(obj, R.layout.auth_dialog)
                obj.binding.viewModelAuth = obj.viewModel
            }
        }
    }

}

