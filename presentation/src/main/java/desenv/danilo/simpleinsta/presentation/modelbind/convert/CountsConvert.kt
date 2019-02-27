package desenv.danilo.simpleinsta.presentation.modelbind.convert

import desenv.danilo.simpleinsta.data.model.User
import desenv.danilo.simpleinsta.presentation.modelbind.CountsBind

object CountsConvert {

    fun fromData(counts: User.Counts?): CountsBind {
        if (counts == null)
            return CountsBind()
        return CountsBind().apply {
            media = counts.media.toString()
            followedBy = counts.followedBy.toString()
            follows = counts.follows.toString()
        }
    }
}