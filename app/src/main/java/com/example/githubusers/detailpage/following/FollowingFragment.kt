package com.example.githubusers.detailpage.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.searchuser.UserAdapter
import com.example.githubusers.databinding.FragmentFollowingBinding
import com.example.githubusers.detailpage.DetailPageActivity

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var viewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailPageActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)
        userAdapter = UserAdapter()

        with(binding) {
            rvDtlFollowing.layoutManager = LinearLayoutManager(activity)
            rvDtlFollowing.adapter = userAdapter
            rvDtlFollowing.setHasFixedSize(true)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.following.observe(viewLifecycleOwner, {
            if(it!=null){
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