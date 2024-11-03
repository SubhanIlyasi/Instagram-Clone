package com.example.instagram.Upload

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram.HomeActivity
import com.example.instagram.Models.PostModel
import com.example.instagram.R
import com.example.instagram.Utils.POST
import com.example.instagram.Utils.POST_FOLDER
import com.example.instagram.Utils.USER_PROFILE_FOLDER
import com.example.instagram.Utils.uploadImage
import com.example.instagram.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Post : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageurl: String = ""
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {

            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageurl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))

            finish()
        }
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            val captionText = binding.caption.text.toString() // Adjust based on the type
            val post = PostModel(imageurl, captionText)
            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser?.uid!!).document().set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
            }
        }


    }

}