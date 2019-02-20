package desenv.danilo.simpleinsta.interfaces

interface AuthenticationListener {
    fun onCodeReceived(auth_token: String)
}