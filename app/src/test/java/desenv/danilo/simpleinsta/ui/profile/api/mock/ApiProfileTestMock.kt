package desenv.danilo.simpleinsta.ui.profile.api.mock

import desenv.danilo.simpleinsta.data.data.apiclient.ApiResponse
import desenv.danilo.simpleinsta.data.data.models.DataMedias
import desenv.danilo.simpleinsta.data.data.models.Location
import desenv.danilo.simpleinsta.data.data.models.Medias
import desenv.danilo.simpleinsta.data.data.models.User
import desenv.danilo.simpleinsta.data.ui.profile.api.ApiProfile
import io.reactivex.Single

class ApiProfileTestMock: ApiProfile {
    var errorType = "Error"
    var errorMessage = "Error Message"
    var code = 200
    var userName = "Danilo is Testing"
    var fullName = "Danilo Little Test"
    var profilePicture = "htttp://themostbeautifulphoto.com"



    override fun getUserData(access_token: String): Single<ApiResponse<User>> {
        return Single.just(
            ApiResponse<User>(
                ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
                User(userName = userName, fullName = fullName, profilePicture = profilePicture),
                ApiResponse.Pagination()))
    }

    override fun getUserPosts(access_token: String): Single<ApiResponse<Array<DataMedias>>> {
        val media1 = DataMedias(Medias(Medias.StantardResolution("https:xxxxxteste.com", 120, 120)),
            User(userName = userName, fullName = fullName, profilePicture = "https:hhhh.com"),
            Location("Rio De Janeiro")
        )

        val media2 = DataMedias(Medias(Medias.StantardResolution("https:xxxxxteste.com", 120, 120)),
            User(userName = userName, fullName = fullName, profilePicture = "https:hhhh.com"),
            Location("Rio De Janeiro")
        )

        val arrayDataMedias = arrayOf(media1, media2)

       return  Single.just(ApiResponse<Array<DataMedias>>(
            ApiResponse.Meta(errorType = errorType, code = code, errorMessage = errorMessage),
            arrayDataMedias,
            ApiResponse.Pagination()))
    }
}