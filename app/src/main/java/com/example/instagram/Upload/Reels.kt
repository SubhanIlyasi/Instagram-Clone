package com.example.instagram.Upload

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram.HomeActivity
import com.example.instagram.Models.PostModel
import com.example.instagram.Models.ReelModel
import com.example.instagram.R
import com.example.instagram.Utils.POST
import com.example.instagram.Utils.POST_FOLDER
import com.example.instagram.Utils.REEL
import com.example.instagram.Utils.REEL_FOLDER
import com.example.instagram.Utils.uploadImage
import com.example.instagram.Utils.uploadVideo
import com.example.instagram.databinding.ActivityReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Reels : AppCompatActivity() {

    // Using lazy initialization for the view binding
    private val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    lateinit var progressDialog :ProgressDialog
    var videoUrl: String = ""
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {

            uploadVideo(uri, REEL_FOLDER,progressDialog ) { url ->
                if (url != null) {

                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            val captionText = binding.caption.text.toString()
            val reel = ReelModel(videoUrl, captionText)
            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser?.uid+ REEL).document().set(reel)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
            }
        }
    }
}
