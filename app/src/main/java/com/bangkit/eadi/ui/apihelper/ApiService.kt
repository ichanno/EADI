package com.bangkit.eadi.ui.apihelper

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun getData(
        @Header("Authorization") token: String
    ) : Call<StoryItemResponse>

    @GET("stories")
    suspend fun getPage(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : StoryItemResponse

    @GET("stories?location=1")
    fun getMap(
        @Header("Authorization") token: String
    ) : Call<StoryItemResponse>

    @GET("stories/{id}")
    fun getStoryDetail(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ) : Call<DetailResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ) : Call<AddStoryResponse>

}