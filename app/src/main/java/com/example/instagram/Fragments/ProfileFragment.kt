package com.example.instagram.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.Adapters.ViewPagerAdapter
import com.example.instagram.Models.UserSignUp
import com.example.instagram.SignUpActivity
import com.example.instagram.Utils.USER_NODE
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    // Declare binding as a class-level variable
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.editProfile.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(MyPostFragment(), "My Posts")
        viewPagerAdapter.addFragment(MyReelsFragment(), "My Reels")
        binding.viewpager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)


        return binding.root
    }

    companion object {
        // You can define static methods or variables here if needed
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {

                val user = it.toObject(UserSignUp::class.java)
                binding.name.text = user!!.name
                binding.bio.text = user.email
                Picasso.get().load(user.image).into(binding.profileImage)

            }
    }
}
