package desenv.danilo.simpleinsta.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.data.model.User
import desenv.danilo.simpleinsta.domain.interactor.user.ListUserPostsUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.LogoutUserUseCase
import desenv.danilo.simpleinsta.domain.interactor.user.ViewUserDetailUseCase
import desenv.danilo.simpleinsta.presentation.adapter.PublicationsAdapter
import desenv.danilo.simpleinsta.presentation.application.ApplicationApp
import desenv.danilo.simpleinsta.presentation.modelbind.TipoList
import desenv.danilo.simpleinsta.presentation.modelbind.UserBind
import desenv.danilo.simpleinsta.presentation.modelbind.convert.DataMediasConvert
import desenv.danilo.simpleinsta.presentation.modelbind.convert.UserConvert
import desenv.danilo.simpleinsta.presentation.state.StateView
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val listUserPostsUseCase: ListUserPostsUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val viewUserDetailUseCase: ViewUserDetailUseCase
): ViewModel(), LifecycleObserver {

    private val stateView = PublishSubject.create<StateView<Array<Unit>>>()
    private var logoutSucess = PublishSubject.create<Boolean>()
    val layoutManager = ObservableField<TipoList>(TipoList.GRID)
    val publicationsAdapter = ObservableField<PublicationsAdapter>(
        PublicationsAdapter(
            arrayOf(),
            TipoList.GRID
        )
    )
    val userBind = UserBind()

    fun getStateView() = stateView

    fun getLogoutSucess() = logoutSucess


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        listUserDetail()
        listUserPost()
    }

    fun listUserDetail() {
        viewUserDetailUseCase.execute(ApplicationApp.mToken,
            {
                updateUser(it)
            },
            {
                stateView.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error, excpetion = it))
            })
    }

    private fun updateUser(userParam: User) {
        val user = UserConvert.fromData(userParam)
        userBind.apply {
            id = user.id
            bio = user.bio
            counts = user.counts
            fullName = user.fullName
            isBussines = user.isBussines
            profilePicture = user.profilePicture
            userName = user.userName
            website = user.website
        }
    }

    fun listUserPost() {
        listUserPostsUseCase.execute(ApplicationApp.mToken,
            onSuccess = {
                updateList(it)
            },
            onError = {
                stateView.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error, excpetion = it))
            })
    }

    fun updateList(it: Array<DataMedias>) {
        val itens = it.map { item -> DataMediasConvert.fromData(item) }
        publicationsAdapter.get()?.posts = itens.toTypedArray()
        publicationsAdapter.get()?.typeList = layoutManager.get()!!
        publicationsAdapter.get()?.notifyChanged()
    }

    fun logout() {
        logoutUserUseCase.execute(ApplicationApp.mSelf!!.applicationContext,
            complete = {
                logoutSucess.onNext(true)
            },
            onError = {
                stateView.onNext(StateView(StateView.Status.ERROR, idMessage = R.string.error_logout, excpetion = it))
            },
            doOnSubscribe = {
                // show progress
            },
            doOnTerminate = {
                // hide progres
            })
    }


    fun changeTypeList(tipoList: TipoList) {
        publicationsAdapter.get()?.typeList = tipoList
        layoutManager.set(tipoList)
        publicationsAdapter.get()?.notifyChanged()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        listUserPostsUseCase.dispose()
        logoutUserUseCase.dispose()
        viewUserDetailUseCase.dispose()
    }


}