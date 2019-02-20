package desenv.danilo.simpleinsta.data.model

import com.google.gson.annotations.SerializedName

class User (
    var id: String ="",
    @SerializedName("username")
    var userName: String ="",
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("profile_picture")
    var profilePicture: String,
    var bio: String ="",
    var website: String ="",
    @SerializedName("is_business")
    var isBussines: Boolean = false,
    var counts: Counts = Counts()
)
{
    class Counts(
        var media: Int = 0,
        var follows: Int = 0,
        @SerializedName("followed_by")
        var followedBy: Int = 0
    )
}