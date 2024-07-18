package com.example.twitterclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class TweetAdapter(var list: List<String>):RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    class TweetViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
val tweet_text = itemView.findViewById<TextView>(R.id.tweet_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
   val view = LayoutInflater.from(parent.context).inflate(R.layout.tweet_list,parent,false)
        return TweetViewHolder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.tweet_text.text = list[position].toString()
    }
}