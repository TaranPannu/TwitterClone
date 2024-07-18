package com.example.twitterclone.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

object Repo {

    var auth: FirebaseAuth =  FirebaseAuth.getInstance()

    fun login(email: String, password: String): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginResult.value = true
                } else {
                    loginResult.value = false
                }
            }
        return loginResult
    }
}