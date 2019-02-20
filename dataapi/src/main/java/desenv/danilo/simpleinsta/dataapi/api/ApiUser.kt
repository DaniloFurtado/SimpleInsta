package desenv.danilo.simpleinsta.dataapi.api

import desenv.danilo.simpleinsta.data.model.ApiResponse
import desenv.danilo.simpleinsta.data.model.DataMedias
import desenv.danilo.simpleinsta.data.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiUser {
    @GET("v1/users/self/")
    fun getUserData(@Query("access_token") access_token: String): Single<ApiResponse<User>>

    //https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN
    @GET("/v1/users/self/media/recent/")
    fun getUserPosts(@Query("access_token") access_token: String): Single<ApiResponse<Array<DataMedias>>>
}