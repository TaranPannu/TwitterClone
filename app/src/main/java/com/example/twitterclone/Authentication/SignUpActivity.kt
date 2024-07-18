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

class SignUpActivity : AppCompatActivity() {
    private lateinit var email: TextView
    private lateinit var pass: TextView
    private lateinit var button: Button
    private lateinit var signUp_btn_login: TextView

    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var firebaseViewModelFactory: FirebaseViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()
        button.setOnClickListener()
        {
            sign_up(email.text.toString(),pass.text.toString())
        }

        signUp_btn_login.setOnClickListener()
        {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sign_up(email: String, password: String) {
        firebaseViewModel.sign_up(email, password).observe(this){
            if(it)
            {
                Toast.makeText(this,"Sign Up Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
               Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun init()
    {
        email = findViewById(R.id.signUp_user_name)
        pass = findViewById(R.id.signUp_password)
        button = findViewById(R.id.signUp_btn_signUp)
        signUp_btn_login = findViewById(R.id.signUp_btn_login)

        firebaseViewModelFactory = FirebaseViewModelFactory(com.example.twitterclone.MVVM.Repo)
        firebaseViewModel = ViewModelProvider(this,firebaseViewModelFactory).get(FirebaseViewModel::class.java)
    }
}

