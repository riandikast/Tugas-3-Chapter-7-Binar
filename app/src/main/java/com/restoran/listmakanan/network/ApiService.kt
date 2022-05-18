package com.restoran.listmakanan.network



import com.listfilm.andika.model.update.UpdateResponse
import com.restoran.listmakanan.model.makanan.GetMenuItem
import com.restoran.listmakanan.model.user.GetUserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("user")
    fun getUser(): Call<List<GetUserItem>>

    @GET("menu-restoran")
    fun getMenu(): Call<List<GetMenuItem>>

    @POST("user")
    @FormUrlEncoded
    fun registerNew(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<GetUserItem>


    @GET("user")
    fun Login(@Query("email") email : String) : Call<List<GetUserItem>>

    @PUT("user/{id}")
    fun updateNewUser(
        @Body user : UpdateResponse, @Path("id") id : String
    ): Call<GetUserItem>




}