package com.example.twitterclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitterclone.Fragments.click_suggest
import com.example.twitterclone.R
import com.example.twitterclone.data.User


class SuggestedApadter(val list: List<User>, val context: Context, val listener: click_suggest): RecyclerView.Adapter<SuggestedApadter.SuggestViewHolder>() {

    class SuggestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
val img:ImageView = itemView.findViewById(R.id.img_adp)
        val email:TextView = itemView.findViewById(R.id.email_adp)
        val btn:Button = itemView.findViewById(R.id.follow_adp)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.suggest_layout, parent, false)
return SuggestViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestViewHolder, position: Int) {
        val i =  list[position]
holder.email.text = i.userEmail
        Glide.with(context).
        load(i.userProfileImage)
            .into(holder.img)

        holder.btn.setOnClickListener()
        {
listener.onFollowClicked(i.uid)
        }
    }

    override fun getItemCount(): Int {
return list.size
    }

}