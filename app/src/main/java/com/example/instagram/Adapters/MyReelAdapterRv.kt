package com.example.instagram.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagram.Models.PostModel
import com.example.instagram.Models.ReelModel
import com.example.instagram.databinding.MyPostRvDesignBinding
import com.squareup.picasso.Picasso

class MyReelAdapterRv(var context: Context, var reelList: ArrayList<ReelModel>) :
    RecyclerView.Adapter<MyReelAdapterRv.ViewHolder>() {

    inner class ViewHolder(var binding: MyPostRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).videoUrl).diskCacheStrategy(
            DiskCacheStrategy.ALL
        ).into(holder.binding.postImage)
    }
}
