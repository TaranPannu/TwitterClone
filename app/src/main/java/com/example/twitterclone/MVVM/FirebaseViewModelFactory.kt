package com.example.twitterclone.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirebaseViewModelFactory(private val repo: Repo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FirebaseViewModel(repo) as T
    }
}


