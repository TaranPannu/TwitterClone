package com.example.twitterclone.MVVM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitterclone.data.User
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.i
import kotlinx.coroutines.launch


class FirebaseViewModel( private val repo: Repo): ViewModel() {

    fun login(email: String, password: String): LiveData<Boolean> {
//        val loginResult = MutableLiveData<Boolean>()
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val res = (repo.login(email, password))
//            loginResult.postValue(res)
//
//        }
        return repo.login(email, password)
    }

    fun isUserLoggedIn(): LiveData<Boolean>
        {
        val loginResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(repo.isUserLoggedIn())
        }
        return loginResult
        }

    fun sign_up(email: String, password: String): LiveData<String>
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