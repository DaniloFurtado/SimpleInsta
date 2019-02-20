package desenv.danilo.simpleinsta.data.models

import com.google.gson.annotations.SerializedName

class Medias  (
    @SerializedName("standard_resolution")
    var stantardResolution: StantardResolution = StantardResolution("", 0, 0)
){
    class StantardResolution(var url: String,
                                   var width: Int = 0,
                                   var height: Int = 0)
}