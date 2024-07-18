package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.twitterclone.Adapters.VPAdapter
import com.example.twitterclone.Authentication.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var floatBtn: FloatingActionButton
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var VP_Adapter: VPAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)  // Get the Toolbar reference
        setSupportActionBar(toolbar)

        init()

        TabLayoutMediator(tabLayout,viewPager)
        {
            tab: TabLayout.Tab, position: Int ->
            when(position)
            {
                0 -> tab.text = "Accounts"
                else -> tab.text = "Tweets"
            }
        }.attach()
        floatBtn.setOnClickListener()
        {
            val intent = Intent(applicationContext,TweetActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> {
                // Handle profile selection
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,ProfileActivity::class.java)
                startActivity(intent)

                return true
            }
            R.id.menu_logout -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                auth.signOut()
                val intent  = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> {
                // Handle other menu items or return false for default behavior
                return super.onOptionsItemSelected(item)
            }
        }
        return true  // This line is unreachable due to `return` statements within the `when` block
    }


    fun init()
    {
        auth = Firebase.auth
        floatBtn = findViewById(R.id.float_btn)
        VP_Adapter = VPAdapter(this)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.View_pager2)
        viewPager.adapter = VP_Adapter
    }
}