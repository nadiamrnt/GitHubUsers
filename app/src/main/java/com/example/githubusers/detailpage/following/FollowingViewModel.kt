package com.example.githubusers.detailpage.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.searchuser.User
import com.example.githubusers.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowingViewModel : ViewModel(){
    private val _following = MutableLiveData<ArrayList<User>>()
    val following: LiveData<ArrayList<User>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingViewModel"
    }

    fun setFollowing (username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : retrofit2.Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        _following.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "${t.message}")
                }
            })
    }
}