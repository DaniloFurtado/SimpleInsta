package desenv.danilo.simpleinsta.ui.di

import dagger.Module
import dagger.Provides
import desenv.danilo.simpleinsta.presentation.AuthVmFactory
import desenv.danilo.simpleinsta.presentation.ProfileVmFactory
import desenv.danilo.simpleinsta.presentation.adapter.PublicationsAdapter
import desenv.danilo.simpleinsta.presentation.modelbind.TipoList
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesPublicationsAdapter() = PublicationsAdapter(arrayOf(), TipoList.GRID)


    @Singleton
    @Provides
    fun providesProfileVmFactory() = ProfileVmFactory()

    @Singleton
    @Provides
    fun providesAuthVmFactory() = AuthVmFactory()
}