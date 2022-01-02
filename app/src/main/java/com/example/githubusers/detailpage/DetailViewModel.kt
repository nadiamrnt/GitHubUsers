package com.example.githubusers.detailpage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubusers.api.ApiConfig
import com.example.githubusers.database.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    companion object {
        const val TAG = "DetailViewModel"
    }

    fun  setUserDetail(username: String?){
        _isLoading.value = true
        if (username != null) {
            _isLoading.value = false
            ApiConfig.getApiService()
                .getDetailUser(username)
                .enqueue(object : retrofit2.Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        if (response.isSuccessful) {
                            _detailUser.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.d(TAG, "${t.message}")
                    }

                })
        }
    }

    fun insert(id: Int?, username: String?, avatar: String?, url: String?) = mFavoriteUserRepository.insert(id, username, avatar, url)
    fun delete(id: Int) = mFavoriteUserRepository.delete(id)

    suspend fun checkUser(id: Int) = mFavoriteUserRepository.checkUser(id)
}