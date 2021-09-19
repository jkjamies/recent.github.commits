package com.example.android.recentgithubcommits

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.recentgithubcommits.models.CommitObject

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CommitObject>?) {
    val adapter = recyclerView.adapter as CommitsAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}