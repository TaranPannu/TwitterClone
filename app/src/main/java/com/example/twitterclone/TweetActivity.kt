package com.example.twitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {

    private lateinit var btn_upload:Button
    private lateinit var tweet_edt:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)
init()
        btn_upload.setOnClickListener()
        {
            upload_tweet1("")
        }    }

    private fun upload_tweet1(tweet:String) {
       FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
           .addListenerForSingleValueEvent(object: ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
             val list = snapshot.child("listOfTweets").value as MutableList<String>
                   list.add(tweet_edt.text.toString())
                   upload_tweet2(list)
               }

               override fun onCancelled(error: DatabaseError) {
                   TODO("Not yet implemented")
               }

           })
    }

    private fun upload_tweet2(list: MutableList<String>) {
FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).child("listOfTweets").setValue(list)
    }

    fun init()
    {
        btn_upload = findViewById(R.id.btn_upload)
        tweet_edt = findViewById(R.id.user_tweet)
    }
}