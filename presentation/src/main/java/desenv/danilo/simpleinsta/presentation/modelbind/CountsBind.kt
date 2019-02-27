package desenv.danilo.simpleinsta.presentation.modelbind

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import desenv.danilo.simpleinsta.presentation.BR


class CountsBind(
    private var _media: String = "",
    private var _follows: String = "",
    private var _followedBy: String = ""
) : BaseObservable() {

    @get:Bindable
    var media: String
        get() = _media
        set(value) {
            _media = value
            notifyPropertyChanged(BR.media)
        }

    @get:Bindable
    var follows: String
        get() = _follows
        set(value) {
            _follows = value
            notifyPropertyChanged(BR.follows)
        }

    @get:Bindable
    var followedBy: String
        get() = _followedBy
        set(value) {
            _followedBy = value
            notifyPropertyChanged(BR.followedBy)
        }
}