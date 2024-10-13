package com.example.instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.signup_btn.setOnClickListener {
            if (binding.name.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("") or
            binding.email.editText?.text.toString().equsls(""))

            {
                Toast.makeText(this@SignUpActivity, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val username = binding.username.text.toString()
                val password = binding.password.text.toString()
                val user = User(username, password)
                val db = DatabaseHandler(this)
                db.insertData(user)
                Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

    }
}