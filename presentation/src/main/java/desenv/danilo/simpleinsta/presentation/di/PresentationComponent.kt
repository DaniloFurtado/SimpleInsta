package desenv.danilo.simpleinsta.presentation.di

import dagger.Component
import desenv.danilo.simpleinsta.presentation.AuthViewModel
import desenv.danilo.simpleinsta.presentation.ProfileViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun initAuthViewModel(): AuthViewModel

    fun initProfileViewModel(): ProfileViewModel
}