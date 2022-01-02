package com.example.githubusers.detailpage.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.searchuser.UserAdapter
import com.example.githubusers.databinding.FragmentFollowersBinding
import com.example.githubusers.detailpage.DetailPageActivity

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var viewModel: FollowersViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailPageActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)
        userAdapter = UserAdapter()

        with(binding) {
            rvDtlFollowers.layoutManager = LinearLayoutManager(activity)
            rvDtlFollowers.adapter = userAdapter
            rvDtlFollowers.setHasFixedSize(true)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.followers.observe(viewLifecycleOwner, {
            if(it != null){
                userAdapter.setList(it)
                binding.apply {
                    noData.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                }
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            noData.visibility = View.INVISIBLE
        }
    }

}