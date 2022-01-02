package com.example.githubusers.detailpage

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("avatar_url")
    val avatar: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("followers")
    val followers: String,

    @field:SerializedName("following")
    val following: String,

    @field:SerializedName("public_repos")
    val repository: String
)
