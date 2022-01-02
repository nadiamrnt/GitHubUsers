package com.example.githubusers.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubusers.database.FavoriteUser
import com.example.githubusers.database.FavoriteUserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorite()
}