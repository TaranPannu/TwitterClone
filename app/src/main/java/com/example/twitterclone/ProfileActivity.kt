package com.example.twitterclone

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime

class ProfileActivity : AppCompatActivity() {
    private lateinit var profile_img:ImageView
    private lateinit var btn_upload:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
init()
        profile_img.setOnClickListener()
        {
            get_Img_from_gallery()
        }
    }

    fun init()
    {
        profile_img = findViewById(R.id.img_user)
        btn_upload = findViewById(R.id.upload_btn)

        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val link = snapshot.child("userProfileImage").value.toString()
                    Glide.with(applicationContext).
                    load(link)
                        .into(profile_img)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun get_Img_from_gallery()
    {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,101)
    }
    @RequiresApi(Build.VERSION_CODES.O)// for Local_Date_time
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK)
        {
            profile_img.setImageURI(data?.data)
        }
        btn_upload.setOnClickListener()
        {
            Upload_Image(data?.data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O) // for Local_Date_time
    fun Upload_Image(data: Uri?)
    {
        val fileName = LocalDateTime.now().toString()+".jpg"
        val StorageRef = FirebaseStorage.getInstance().reference.child("Profile_Images/$fileName")
        StorageRef.putFile(data!!).addOnSuccessListener {
            Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
 updateProfilePic(it.toString())
            }
        }
    }

    fun updateProfilePic(img:String)
    {
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
            .child("userProfileImage").setValue(img)
    }

}