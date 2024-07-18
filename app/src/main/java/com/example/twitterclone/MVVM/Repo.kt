package com.example.twitterclone.MVVM

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.twitterclone.Adapters.SuggestedApadter
import com.example.twitterclone.HomeActivity
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Repo {

    var auth: FirebaseAuth =  Firebase.auth
    var database: FirebaseDatabase =  Firebase.database

    fun login(email: String, password: String): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()

        if (auth.currentUser!= null)
        {
            loginResult.value = true
        }
        else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginResult.value = true
                    } else {
                        loginResult.value = false
                    }
                }
        }
        return loginResult
    }

 fun isUserLoggedIn():LiveData<Boolean> {
     val loginResult = MutableLiveData<Boolean>()
     GlobalScope.launch(Dispatchers.IO) {
     if (auth.currentUser != null) {
            loginResult.value = true
        } else {
            loginResult.value = false
        }
     }
        return loginResult
    }


    fun sign_up(email: String, password: String):LiveData<Boolean> {
        val SignInResult = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task ->
            if (task.isSuccessful)
            {
                var listOfFollowings: List<String> = mutableListOf("")
                val user = User(email,"",listOfFollowings,listOfFollowings,auth.uid.toString())
                database.getReference("users").child(user.uid).setValue(
                    user
                ).addOnSuccessListener{
                    SignInResult.value = true
                }.addOnFailureListener(){
                    SignInResult.value = false
                }
            } else { SignInResult.value = false }
        }
        return SignInResult
    }

    fun getAllUsers():MutableLiveData<User>
    {
        val user_list = MutableLiveData<User>()
        FirebaseDatabase.getInstance().reference.child("users").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children){
                    val currUser = snap.getValue(User::class.java)
                    if (currUser != null) {
                        user_list.value = currUser
                    } } }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            } })
        return user_list
    }


        fun onFollowClicked(uid: String): LiveData<Boolean> {
            val followResult = MutableLiveData<Boolean>()
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.child("listOfFollowings").value as MutableList<String>
                    if(!list.contains(uid) && uid!=Firebase.auth.uid.toString())
                    {
                        list.add(uid)
                        follow_user(list)
                        followResult.value = true
                    }
                    else
                    {
                        followResult.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    followResult.value = false
                }

            })

            return followResult
    }
    private fun follow_user(list: MutableList<String>) {
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).child("listOfFollowings").setValue(list)
//        Toast.makeText(context,"Followed", Toast.LENGTH_SHORT).show()
    }

}