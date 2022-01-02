package com.example.githubusers.searchuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.detailpage.DetailPageActivity
import com.example.githubusers.favorite.FavoriteUserActivity
import com.example.githubusers.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView = binding.searchView

        adapter = UserAdapter()
        with(adapter) {
            notifyDataSetChanged()
            setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClick(data: User) {
                    val detailIntent = Intent(this@MainActivity, DetailPageActivity::class.java).also {
                        it.putExtra(DetailPageActivity.EXTRA_USERNAME, data.username)
                        it.putExtra(DetailPageActivity.EXTRA_ID, data.id)
                        it.putExtra(DetailPageActivity.EXTRA_AVATAR, data.avatar)
                        it.putExtra(DetailPageActivity.EXTRA_URL, data.url)
                    }
                    startActivity(detailIntent)
                }

            })
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.adapter = adapter
            rvUser.setHasFixedSize(true)
        }

        searchUser()
        optionMenu()

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        viewModel.listUsers.observe(this, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun optionMenu() {
        binding.btnMenu.setOnClickListener{
            val popupMenu = PopupMenu(this, binding.btnMenu)
            popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.favorite_user -> {
                        val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.settings -> {
                        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun searchUser() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isEmpty()) {
                    true
                } else {
                    viewModel.setSearchUser(query)
                    true
                }
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if(isLoading) View.VISIBLE else View.INVISIBLE
            lottieSearch.visibility = View.INVISIBLE
            lottieOctocat.visibility = View.INVISIBLE
        }
    }
}