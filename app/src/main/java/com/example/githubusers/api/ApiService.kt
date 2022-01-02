package com.example.githubusers.api

import com.example.githubusers.detailpage.DetailUserResponse
import com.example.githubusers.searchuser.User
import com.example.githubusers.searchuser.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUser(
        @Path("username") query: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowers(
        @Path("username") query: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowing(
        @Path("username") query: String
    ): Call<ArrayList<User>>
}