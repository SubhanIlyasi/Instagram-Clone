package com.example.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram.Adapters.MyPostRvAdapter
import com.example.instagram.Models.PostModel
import com.example.instagram.R
import com.example.instagram.databinding.FragmentMyPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MyPostFragment : Fragment() {

    private lateinit var binding: FragmentMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        var postList = ArrayList<PostModel>()
        var adapter = MyPostRvAdapter(requireContext(), postList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
        var tempList = arrayListOf<PostModel>()
            for (i in it.documents){
                var post = i.toObject(PostModel::class.java)
                tempList.add(post!!)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {


    }
}