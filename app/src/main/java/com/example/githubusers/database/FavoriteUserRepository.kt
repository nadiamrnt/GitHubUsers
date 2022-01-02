package com.example.githubusers.database

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserRepository (application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavorite():LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavorite()

    fun insert(id: Int?, username: String?, avatar: String?, url: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser( id, username, avatar, url)
            mFavoriteUserDao.insert(user)
        }
    }

    fun delete(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteUserDao.delete(id)
        }
    }

    suspend fun checkUser(id: Int) = mFavoriteUserDao.checkUser(id)
}