package com.example.android.recentgithubcommits.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.recentgithubcommits.GitHubCommitsApplication
import com.example.android.recentgithubcommits.R
import com.example.android.recentgithubcommits.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModels<MainActivityViewModel> {
        MainActivityViewModelFactory(
            requireContext().applicationContext as GitHubCommitsApplication
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = CommitsAdapter()

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBar.isVisible = loading
        })

        binding.searchFAB.setOnClickListener {
            viewModel.commitsApiCall()
        }

        viewModel.commitsApiCall()

        return binding.root
    }

}