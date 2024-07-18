package com.example.twitterclone.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitterclone.data.User
import kotlinx.coroutines.Dispatchers


class FirebaseViewModel( private val repo: Repo): ViewModel() {

    fun login(email: String, password: String): LiveData<Boolean>
    {
        return repo.login(email, password) // this will be live Data, we have created it in Repo
    }

    fun isUserLoggedIn():LiveData<Boolean>{

        return repo.isUserLoggedIn()
    }

    fun sign_up(email: String, password: String): LiveData<Boolean>
    {
        return repo.sign_up(email, password)
    }

    fun getAllUsers():MutableLiveData<User>
    {
        return repo.getAllUsers()
    }

    fun onFollowClicked(uid: String):LiveData<Boolean>
    {
        return repo.onFollowClicked(uid)
    }

}