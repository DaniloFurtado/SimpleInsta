package desenv.danilo.simpleinsta.presentation.modelbind

import java.io.Serializable

class DataMediasBind(
    var images: MediasBind,
    var user: UserBind,
    var location: LocationBind
) : Serializable

