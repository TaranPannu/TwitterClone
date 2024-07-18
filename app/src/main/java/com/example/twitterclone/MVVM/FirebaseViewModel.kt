package com.example.twitterclone.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FirebaseViewModel( private val repo: Repo): ViewModel() {

    fun login(email: String, password: String): LiveData<Boolean>
    {
return repo.login(email, password) // this will be live Data, we have created it in Repo
    }
}