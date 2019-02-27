package desenv.danilo.simpleinsta.presentation.modelbind

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import desenv.danilo.simpleinsta.presentation.BR

class LocationBind(private var _name: String = "") : BaseObservable() {

    @get:Bindable
    var name: String
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }
}