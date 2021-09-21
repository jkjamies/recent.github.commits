package com.example.android.recentgithubcommits.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.recentgithubcommits.GitHubCommitsApplication
import com.example.android.recentgithubcommits.R
import com.example.android.recentgithubcommits.data.CommitsRepository
import com.example.android.recentgithubcommits.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModels<MainFragmentViewModel> {
        MainFragmentViewModel.MainFragmentViewModelFactory(
            (requireContext().applicationContext as GitHubCommitsApplication)
                .commitsRepository
        )
    }

    @Inject
    lateinit var commitsRepository: CommitsRepository

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
            viewModel.getCommits(true)
            hideKeyboard()
        }

        // On first load, will not fetch, but this is here to demonstrate working database
        // after fetching using FAB, close and re-open app to see it will refresh from the
        // observed local data source (database)
        viewModel.getCommits(false)

        return binding.root
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}