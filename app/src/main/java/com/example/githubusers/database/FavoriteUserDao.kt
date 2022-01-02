package com.example.githubusers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun delete(id: Int)

    @Query ("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query ("SELECT * FROM favorite_user ORDER BY id DESC")
    fun getAllFavorite() : LiveData<List<FavoriteUser>>
}