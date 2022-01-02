package com.example.githubusers.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.database.FavoriteUser
import com.example.githubusers.databinding.ActivityFavoriteUserBinding
import com.example.githubusers.detailpage.DetailPageActivity
import com.example.githubusers.searchuser.User
import com.example.githubusers.searchuser.UserAdapter

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClick(data: User) {
                val detailIntent = Intent(this@FavoriteUserActivity, DetailPageActivity::class.java). also {
                    it.putExtra(DetailPageActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailPageActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailPageActivity.EXTRA_AVATAR, data.avatar)
                    it.putExtra(DetailPageActivity.EXTRA_URL, data.url)
                }
                startActivity(detailIntent)
            }
        })

        with(binding) {
            rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavUser.adapter = adapter
            rvFavUser.setHasFixedSize(true)
        }

        viewModel.getAllFavorite()?.observe(this, {
            if (it != null) {
                val list = ConvertToList.convert(it)
                adapter.setList(list)
            }
        })
    }

    object ConvertToList {
        fun convert(users: List<FavoriteUser>): ArrayList<User> {
            val listUser = ArrayList<User>()
            for(user in users){
                val userMapped = User(
                    user.username,
                    user.id,
                    user.avatar,
                    user.url
                )
                listUser.add(userMapped)
            }
            return listUser
        }
    }
}