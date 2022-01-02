package com.example.githubusers.searchuser

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("avatar_url")
    val avatar: String?,

    @field:SerializedName("html_url")
    val url: String?
)
