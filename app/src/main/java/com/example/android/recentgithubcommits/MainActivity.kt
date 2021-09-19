package com.example.android.recentgithubcommits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.recentgithubcommits.databinding.ActivityMainBinding
import com.example.android.recentgithubcommits.models.CommitObject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CommitsAdapter
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(
            this,
            MainActivityViewModel.Factory(activity.application)
        ).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        viewModel.commitsApiCall()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CommitsAdapter()
    }

//    private fun initViewModel() {
//        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
//        viewModel.getLiveDataObserver().observe(this,
//            Observer<List<CommitObject>> { t ->
//                if (t != null) {
//                    adapter.updateCommitsData(t)
//                    adapter.notifyDataSetChanged()
//                }
//            })
//        viewModel.commitsApiCall()
//    }
}