package desenv.danilo.simpleinsta.data.model

import com.google.gson.annotations.SerializedName

class ApiResponse<T>(
    val meta: Meta,
    val data: T,
    val pagination: Pagination
){
    class Pagination (
        @SerializedName("next_url")
        var nextUrl: String = "",
        @SerializedName("next_max_id")
        var nextMaxId: Int = 0
    )

    class Meta(
        @SerializedName("error_type")
        var errorType: String? = "",
        var code : Int,
        @SerializedName("error_message")
        var errorMessage: String =""

    )
}
