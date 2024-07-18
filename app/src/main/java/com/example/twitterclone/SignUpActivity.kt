package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    private lateinit var email: TextView
    private lateinit var pass: TextView
    private lateinit var button: Button
    private lateinit var signUp_btn_login: TextView

    private lateinit var auth: FirebaseAuth
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
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sign_up(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                task ->
            if (task.isSuccessful)
            {
                var listOfFollowings: List<String> = mutableListOf("Hello")
                val user = User(email,"",listOfFollowings,listOfFollowings,auth.uid.toString())

                uploadUserInfo(user)
                Toast.makeText(this,"Sign Up Successfully", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadUserInfo( user: User) {
        Firebase.database.getReference("users").child(user.uid).setValue(
           user
        ).addOnSuccessListener{
            Toast.makeText(applicationContext,"Uploaded Successfully",Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener(){
            Toast.makeText(applicationContext,"Uploading Failed",Toast.LENGTH_SHORT).show()
        }
    }

    fun init()
    {
        email = findViewById(R.id.signUp_user_name)
        pass = findViewById(R.id.signUp_password)
        button = findViewById(R.id.signUp_btn_signUp)
        signUp_btn_login = findViewById(R.id.signUp_btn_login)

        auth = Firebase.auth
    }
}

