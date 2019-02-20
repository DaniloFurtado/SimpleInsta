package desenv.danilo.simpleinsta.presentation.state

class StateView<T>(
    var state:Status,
    var data: T? = null,
    var idMessage: Int = 0
) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}