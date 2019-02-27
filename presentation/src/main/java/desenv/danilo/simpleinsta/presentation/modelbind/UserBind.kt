package desenv.danilo.simpleinsta.presentation.modelbind

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import desenv.danilo.simpleinsta.presentation.BR


class UserBind(
    private var _id: String = "",
    private var _userName: String = "",
    private var _fullName: String = "",
    private var _profilePicture: String = "",
    private var _bio: String? = "",
    private var _website: String? = "",
    private var _isBussines: Boolean = false,
    private var _counts: CountsBind = CountsBind()
) : BaseObservable() {

    @get:Bindable
    var id: String
        get() = _id
        set(value) {
            _id = value
            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    var userName: String
        get() = _userName
        set(value) {
            _userName = value
            notifyPropertyChanged(BR.userName)
        }

    @get:Bindable
    var fullName: String
        get() = _fullName
        set(value) {
            _fullName = value
            notifyPropertyChanged(BR.fullName)
        }

    @get:Bindable
    var profilePicture: String
        get() = _profilePicture
        set(value) {
            _profilePicture = value
            notifyPropertyChanged(BR.profilePicture)
        }

    @get:Bindable
    var bio: String?
        get() = _bio
        set(value) {
            _bio = value
            notifyPropertyChanged(BR.bio)
        }

    @get:Bindable
    var website: String?
        get() = _website
        set(value) {
            _website = value
            notifyPropertyChanged(BR.website)
        }

    @get:Bindable
    var isBussines: Boolean
        get() = _isBussines
        set(value) {
            _isBussines = value
            notifyPropertyChanged(BR.bussines)
        }

    @get:Bindable
    var counts: CountsBind
        get() = _counts
        set(value) {
            _counts = value
            notifyPropertyChanged(BR.counts)
        }
}