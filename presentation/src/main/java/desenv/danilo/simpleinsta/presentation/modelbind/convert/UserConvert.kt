package desenv.danilo.simpleinsta.presentation.modelbind.convert

import desenv.danilo.simpleinsta.data.model.User
import desenv.danilo.simpleinsta.presentation.modelbind.UserBind

object UserConvert {

    fun fromData(user: User): UserBind {
        return UserBind().apply {
            id = user.id
            bio = user.bio
            counts = CountsConvert.fromData(user.counts)
            fullName = user.fullName
            isBussines = user.isBussines
            profilePicture = user.profilePicture
            userName = user.userName
            website = user.website
        }
    }
}