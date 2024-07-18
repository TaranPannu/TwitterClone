package com.example.twitterclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SuggestedAccountFragment : Fragment(), click_suggest {

    private lateinit var suggestedApadter: SuggestedApadter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

   val view = inflater.inflate(R.layout.fragment_suggested_account, container, false)
        init(view)
        var list:MutableList<User> = mutableListOf()
        FirebaseDatabase.getInstance().reference.child("users").addListenerForSingleValueEvent(object:
            ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
             for (snap in snapshot.children){
                 val currUser = snap.getValue(User::class.java)
                 if (currUser != null) {
                     list.add(currUser)
                 }
             }
                val apadter = context?.let { SuggestedApadter(list, it,this@SuggestedAccountFragment) }

                recyclerView.adapter = apadter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))
//        list.add(User("a@gmail.com","https://firebasestorage.googleapis.com/v0/b/twitterclone-80519.appspot.com/o/Profile_Images%2F2024-07-17T15%3A57%3A29.248.jpg?alt=media&token=78f195e0-6fac-4520-b38d-a8a1ca4f8066",listOf(),listOf(),""))

        return view
    }


    fun init(view: View)
    {
        recyclerView = view.findViewById(R.id.rcy_suggest_fragment)
        recyclerView.layoutManager = GridLayoutManager(context,3)
    }

    override fun onFollowClicked(uid: String) {
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.child("listOfFollowings").value as MutableList<String>
                    if(!list.contains(uid) && uid!=Firebase.auth.uid.toString()) {
                        list.add(uid)
                        follow_user(list)
                    }
                    else{
                        Toast.makeText(context,"Already Followed Account", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun follow_user(list: MutableList<String>) {
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).child("listOfFollowings").setValue(list)
        Toast.makeText(context,"Followed", Toast.LENGTH_SHORT).show()

    }
}

interface click_suggest
{
    fun onFollowClicked(uid: String)
}

