package com.example.android.recentgithubcommits.main.models

import androidx.databinding.BaseObservable

data class RepositoryOwner (
    var ownerName: String,
    var repositoryName: String
) : BaseObservable()