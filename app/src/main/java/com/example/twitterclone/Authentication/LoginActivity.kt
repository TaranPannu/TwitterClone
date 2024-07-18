package com.example.twitterclone.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.twitterclone.HomeActivity
import com.example.twitterclone.MVVM.FirebaseViewModel
import com.example.twitterclone.MVVM.FirebaseViewModelFactory
import com.example.twitterclone.R

class LoginActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var pass: TextView
    private lateinit var button: Button
    private lateinit var btn_sign_up: TextView


    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var firebaseViewModelFactory: FirebaseViewModelFactory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        button.setOnClickListener()
        {
            login(email.text.toString(),pass.text.toString())
        }

        btn_sign_up.setOnClickListener()
        {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        firebaseViewModel.isUserLoggedIn().observe(this){
            if(it)
            {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish() // we want to destroy the Login Activity Here
             }
        }

        /**
         * this is used in the observe() method to specify the current activity or fragment
         * as the lifecycle owner for the LiveData. It ensures that the observer is active
         * only when the activity or fragment is in a valid lifecycle state, helping to manage
         * memory efficiently and avoid potential memory leaks.
         */
    }
    private fun login(email: String, password: String)
    {
             firebaseViewModel.login(email, password).observe(this)
            {
                 if(it) {
                     val intent = Intent(applicationContext, HomeActivity::class.java)
                     startActivity(intent)
                     finish()
                     Toast.makeText(this, "Login in Successfully", Toast.LENGTH_SHORT).show()
                 }
                else
                 {
                     Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                 }
            }

    }
    fun init()
    {
        email = findViewById(R.id.login_user_name)
        pass = findViewById(R.id.login_password)
        button = findViewById(R.id.login_btn_login)
        btn_sign_up = findViewById(R.id.btn_sign_up)

        firebaseViewModelFactory = FirebaseViewModelFactory(com.example.twitterclone.MVVM.Repo)
        firebaseViewModel = ViewModelProvider(this,firebaseViewModelFactory).get(FirebaseViewModel::class.java)
    }

    /**
     * .get(FirebaseViewModel::class.java): This method returns an instance of FirebaseViewModel.
     * If the ViewModel already exists, it returns the existing instance; otherwise,
     * it uses the factory to create a new instance.
     *
     * Purpose:
     * This line retrieves or creates an instance of FirebaseViewModel associated with the activity or fragment.
     * It ensures that the ViewModel has a scoped lifecycle and retains data across configuration changes
     * (like screen rotations).
     */
}