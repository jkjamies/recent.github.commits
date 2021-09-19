package com.example.android.recentgithubcommits

import androidx.databinding.BaseObservable

data class RepositoryOwner (
    var ownerName: String,
    var repositoryName: String
) : BaseObservable()