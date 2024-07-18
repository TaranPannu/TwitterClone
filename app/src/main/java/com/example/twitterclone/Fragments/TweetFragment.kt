package com.example.twitterclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.Adapters.TweetAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetFragment : Fragment() {
    private lateinit var tweetApadter: TweetAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tweet, container, false)
        init(view)

        var list = mutableListOf<String>()
        FirebaseDatabase.getInstance().reference.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        val ad = TweetAdapter(list)
        recyclerView.adapter = ad

  return view
    }



    fun init(view: View)
    {
        recyclerView = view.findViewById(R.id.rcy_tweet_fragment)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

}