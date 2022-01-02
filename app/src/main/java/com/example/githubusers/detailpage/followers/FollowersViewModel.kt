package com.example.githubusers.detailpage.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.searchuser.User
import com.example.githubusers.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followers = MutableLiveData<ArrayList<User>>()
    val followers: LiveData<ArrayList<User>> = _followers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    fun setFollowers(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : retrofit2.Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    _isLoading.value = false
                    _followers.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "${t.message}")
                }
            })
    }
}