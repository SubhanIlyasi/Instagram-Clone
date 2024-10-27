package com.example.instagram

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram.Models.UserSignUp
import com.example.instagram.Utils.USER_NODE
import com.example.instagram.Utils.USER_PROFILE_FOLDER
import com.example.instagram.Utils.uploadImage
import com.example.instagram.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso


class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    lateinit var user: UserSignUp
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {
                } else {
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        user = UserSignUp()
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.signupBtn.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject(UserSignUp::class.java)
                        user?.let {
                            binding.name.editText?.setText(it.name)
                            binding.email.editText?.setText(it.email)
                            binding.password.editText?.setText(it.password)


                            Picasso.get().load(it.image).into(binding.profileImage)
                        }
                    }
            }
        }

        binding.signupBtn.setOnClickListener {
            if (intent.getIntExtra("MODE", -1) == 1) {
                // Update Profile Logic
                user.name = binding.name.editText?.text.toString()
                user.email = binding.email.editText?.text.toString()
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@SignUpActivity, "Failed to update user: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // Registration Logic
                if (binding.name.editText?.text.isNullOrEmpty() || binding.password.editText?.text.isNullOrEmpty() || binding.email.editText?.text.isNullOrEmpty()) {
                    Toast.makeText(this@SignUpActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseUser = FirebaseAuth.getInstance().currentUser
                            firebaseUser?.let {
                                user.name = binding.name.editText?.text.toString()
                                user.email = binding.email.editText?.text.toString()
                                Firebase.firestore.collection(USER_NODE).document(it.uid).set(user)
                                    .addOnSuccessListener {
                                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                        finish()
                                    }
                            }
                        } else {
                            Toast.makeText(this@SignUpActivity, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.addProfile.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }

}