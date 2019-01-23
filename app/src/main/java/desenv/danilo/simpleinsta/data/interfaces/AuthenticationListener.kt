package desenv.danilo.simpleinsta.data.interfaces

interface AuthenticationListener {
    fun onCodeReceived(auth_token: String)
}