package desenv.danilo.simpleinsta.data.model

import java.io.Serializable

class DataMedias(var images: Medias,
                 var user: User,
                 var location: Location?
) : Serializable



class Location(var name: String = "")