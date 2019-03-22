package desenv.danilo.simpleinsta.presentation.di

import dagger.Module
import dagger.Provides
import desenv.danilo.simpleinsta.data.AuthRepository
import desenv.danilo.simpleinsta.data.UserRepository
import desenv.danilo.simpleinsta.dataapi.ApiUserRepository
import desenv.danilo.simpleinsta.dataapi.api.APIClient
import desenv.danilo.simpleinsta.dataapi.api.ApiUser
import desenv.danilo.simpleinsta.dataresource.RsAuthRepository
import desenv.danilo.simpleinsta.domain.executor.BaseSchedulerProvider
import desenv.danilo.simpleinsta.presentation.executor.SchedulerProvider
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class PresentationModule {

    @Provides
    fun providesBaseSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Singleton
    @Provides
    fun providesRsAuthRepository(): AuthRepository = RsAuthRepository()

    @Singleton
    @Provides
    fun providesApiUserRepository(apiUser: ApiUser): UserRepository = ApiUserRepository(apiUser)

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit = APIClient.getAPIService()

    @Singleton
    @Provides
    fun providesApiUser(retrofit: Retrofit): ApiUser = retrofit.create(ApiUser::class.java)
}