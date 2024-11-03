package com.example.instagram.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram.Adapters.MyPostRvAdapter
import com.example.instagram.Adapters.MyReelAdapterRv
import com.example.instagram.Models.PostModel
import com.example.instagram.Models.ReelModel
import com.example.instagram.R
import com.example.instagram.Utils.REEL
import com.example.instagram.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class MyReelsFragment : Fragment() {

    private lateinit var binding: FragmentMyReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyReelsBinding.inflate(inflater, container, false)
        var reelList = ArrayList<ReelModel>()
        var adapter = MyReelAdapterRv(requireContext(), reelList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).get()
            .addOnSuccessListener {
                var tempList = arrayListOf<ReelModel>()
                for (i in it.documents) {
                    var post = i.toObject(ReelModel::class.java)
                    tempList.add(post!!)
                }
                reelList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {

    }
}