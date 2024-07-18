package com.example.twitterclone.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.Adapters.SuggestedApadter
import com.example.twitterclone.MVVM.FirebaseViewModel
import com.example.twitterclone.MVVM.FirebaseViewModelFactory
import com.example.twitterclone.MVVM.Repo
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SuggestedAccountFragment : Fragment(), click_suggest {

    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var firebaseViewModelFactory: FirebaseViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_suggested_account, container, false)
        init(view)
        var list:MutableList<User> = mutableListOf()
        val apadter = context?.let { SuggestedApadter(list, it,this@SuggestedAccountFragment) }

        firebaseViewModel.getAllUsers().observe(viewLifecycleOwner)
        {
            list.add(it)
                recyclerView.adapter = apadter
        }
        return view
    }
    fun init(view: View)
    {
        recyclerView = view.findViewById(R.id.rcy_suggest_fragment)
        recyclerView.layoutManager = GridLayoutManager(context,3)

        firebaseViewModelFactory = FirebaseViewModelFactory(Repo)
        firebaseViewModel = ViewModelProvider(this,firebaseViewModelFactory).get(FirebaseViewModel::class.java)
    }

    override fun onFollowClicked(uid: String) {
        firebaseViewModel.onFollowClicked(uid).observe(viewLifecycleOwner)
        {
            if(it)
            {
                Toast.makeText(context,"Succesfully Followed", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context,"Already Followed Account or Network Issue", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

interface click_suggest
{
    fun onFollowClicked(uid: String)
}

