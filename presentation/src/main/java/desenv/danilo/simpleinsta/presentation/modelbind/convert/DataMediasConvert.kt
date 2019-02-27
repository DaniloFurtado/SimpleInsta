package desenv.danilo.simpleinsta.presentation.modelbind.convert

import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.presentation.modelbind.DataMediasBind
import desenv.danilo.simpleinsta.presentation.modelbind.LocationBind
import desenv.danilo.simpleinsta.presentation.modelbind.MediasBind
import desenv.danilo.simpleinsta.presentation.modelbind.StantardResolutionBind

object DataMediasConvert {

    fun fromData(media: DataMedias): DataMediasBind {
        val image = MediasBind().apply {
            stantardResolution = StantardResolutionBind(
                url = media.images.stantardResolution.url,
                width = media.images.stantardResolution.width,
                height = media.images.stantardResolution.height
            )
        }
        val locationName = if (media.location != null) {
            media.location!!.name
        } else {
            ""
        }
        return DataMediasBind(
            images = image,
            user = UserConvert.fromData(media.user),
            location = LocationBind(locationName)
        )
    }
}