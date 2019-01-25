package desenv.danilo.simpleinsta.data.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import desenv.danilo.simpleinsta.data.application.ApplicationApp
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class ProfileViewModel(private val repository: ProfileRepository,
                       private val processScheduler: Scheduler, private val androidScheduler: Scheduler
): ViewModel(), LifecycleObserver {

    private val disposable = CompositeDisposable()
    val actionError      = PublishSubject.create<String>()
    var fullName = ObservableField<String>()
    var followers = ObservableField<String>()
    var following = ObservableField<String>()
    var profilePicture = ObservableField<String>()
    var mediaPosts     = ObservableField<String>()
    var publicationsAdapter =  ObservableField<PublicationsAdapter>(PublicationsAdapter(emptyArray(), TipoList.GRID))
    var layoutManager =  ObservableField<TipoList>(TipoList.GRID)
    var logoutSucess  = PublishSubject.create<Boolean>()



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate(){
        retrieveData()
    }

    private fun retrieveData() {
        retrieveDataProfileUser(ApplicationApp.mToken ?: "")

        retrieveDataPotsUser()
    }

    fun retrieveDataPotsUser() {
        disposable.add(
            repository
                .getUserPosts(ApplicationApp.mToken ?: "")
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribeBy(
                    onSuccess= {
                        if (it.meta.code == 200) {
                            publicationsAdapter.get()?.posts = it.data
                            publicationsAdapter.get()?.notifyChanged()
                        } else {
                            actionError.onNext(it.meta.errorMessage)
                        }
                    },
                    onError = {
                        actionError.onNext(it.message ?: "Error retriveing User posts")
                    })
        )
    }

    fun retrieveDataProfileUser(token: String) {
        disposable.add(
            repository
                .getUserData(token)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribeBy(
                    onSuccess ={
                        if (it.meta.code == 200) {
                            fullName.set(it.data.fullName)
                            profilePicture.set(it.data.profilePicture)
                            followers.set(it.data.counts.followedBy.toString())
                            following.set(it.data.counts.follows.toString())
                            mediaPosts.set(it.data.counts.media.toString())
                        } else {
                            actionError.onNext(it.meta.errorMessage)
                        }
                    },
                    onError = {
                        actionError.onNext("Error retriveing User data")
                    })
        )
    }

    fun logout() {
        disposable.add(repository
            .logout()
            .subscribeOn(processScheduler)
            .observeOn(androidScheduler)
            .subscribeBy(
                onSuccess = {
                    if (it)
                        logoutSucess.onNext(true)
                    else
                        actionError.onNext( "Error logout")
                },
                onError = {
                    val message = if(it.message != null && it.message.toString().isNotEmpty()) it.message else "Error logout"

                    actionError.onNext(message!!)
                }
            )
        )
    }

    fun changeTypeList(tipoList: TipoList){
        if (publicationsAdapter.get()?.typeList != tipoList){
            layoutManager.set(tipoList)
            publicationsAdapter.get()?.typeList = tipoList
            publicationsAdapter.get()?.notifyChanged()

        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy(){
        disposable.clear()
    }


    class Factory(private val profileRepository: ProfileRepository)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = ProfileViewModel(profileRepository, Schedulers.io(), AndroidSchedulers.mainThread()) as T
    }
}