package com.example.android.recentgithubcommits.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.recentgithubcommits.main.CommitsAdapter.ViewHolder
import com.example.android.recentgithubcommits.databinding.ListItemBinding
import com.example.android.recentgithubcommits.models.CommitObject

class CommitsAdapter : ListAdapter<CommitObject, ViewHolder>(CommitDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(commit: CommitObject) {
            binding.commit = commit
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CommitDiffCallback : DiffUtil.ItemCallback<CommitObject>() {
    override fun areItemsTheSame(oldItem: CommitObject, newItem: CommitObject): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: CommitObject, newItem: CommitObject): Boolean {
        return oldItem == newItem
    }
}