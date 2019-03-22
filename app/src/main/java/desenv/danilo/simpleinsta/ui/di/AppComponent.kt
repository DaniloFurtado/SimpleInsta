package desenv.danilo.simpleinsta.ui.di

import dagger.Component
import desenv.danilo.simpleinsta.ui.AuthenticationActivity
import desenv.danilo.simpleinsta.ui.ProfileActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun init(activity: ProfileActivity)

    fun init(activity: AuthenticationActivity)
}