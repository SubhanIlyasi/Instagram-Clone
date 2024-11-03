package com.example.instagram.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.R
import com.example.instagram.Upload.Post
import com.example.instagram.Upload.Reels
import com.example.instagram.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), Post::class.java))
            activity?.finish()
        }
        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), Reels::class.java))
        }
        return binding.root
    }

    companion object {

    }
}