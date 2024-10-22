package com.example.twitterclone.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.twitterclone.Fragments.SuggestedAccountFragment
import com.example.twitterclone.Fragments.TweetFragment

class VPAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position) {
            0 -> SuggestedAccountFragment()
            else -> TweetFragment()
        }
    }
}