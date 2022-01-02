package com.example.githubusers.searchuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<ArrayList<User>>()
    val listUsers: LiveData<ArrayList<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUser(query: String){
        _isLoading.value = true
        ApiConfig.getApiService()
                .getSearchUsers(query)
                .enqueue(object : retrofit2.Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        _isLoading.value = false
                        _listUsers.postValue(response.body()?.items)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.d(TAG, "${t.message}")
                    }
                })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}