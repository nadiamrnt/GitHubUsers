package com.example.githubusers.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.databinding.ActivityDetailPageBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPageBinding
    private lateinit var viewModel: DetailViewModel

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dtlUsername = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val url = intent.getStringExtra(EXTRA_URL)
        var _isChecked = false
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, dtlUsername)


        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.setUserDetail(username = dtlUsername)
        viewModel.detailUser.observe(this, {
            if(it != null){
                with(binding){
                    tvDtlName.text = it.name ?: "-"
                    tvDtlFollowing.text = it.following
                    tvDtlFollowers.text = it.followers
                    tvDtlCompany.text = it.company ?: "-"
                    tvDtlLocation.text = it.location ?: "-"
                    tvDtlRepository.text = it.repository
                    Glide.with(root.context)
                        .load(it.avatar)
                        .circleCrop()
                        .into(ivDtlAvatar)
                }
                setActionBarTitle(it.username)
            }
        })
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        tabLayout(bundle)

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null) {
                    if (count > 0) {
                        binding.btnFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.btnFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.btnFav.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked) {
                viewModel.insert(id = id, username = dtlUsername, avatar = avatar, url = url)
                Toast.makeText(this@DetailPageActivity, "Added to Favorite List", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.delete(id = id)
                Toast.makeText(this@DetailPageActivity, "Remove from Favorite List", Toast.LENGTH_SHORT).show()
            }
            binding.btnFav.isChecked = _isChecked
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if(isLoading) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun tabLayout(bundle: Bundle){

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){tab, position->
            tab.text = resources.getString(TABS[position])
        }.attach()

    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_URL = "extra_url"

        @StringRes
        private val TABS = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}