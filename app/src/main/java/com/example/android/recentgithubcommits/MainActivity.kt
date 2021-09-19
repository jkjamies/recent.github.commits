package com.example.android.recentgithubcommits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.recentgithubcommits.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.commitsApiCall()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CommitsAdapter()
    }
}