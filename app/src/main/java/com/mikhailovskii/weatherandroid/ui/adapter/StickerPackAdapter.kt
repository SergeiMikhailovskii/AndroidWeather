package com.mikhailovskii.weatherandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.sticker_layout.view.*


class StickerPackAdapter : RecyclerView.Adapter<StickerPackAdapter.ViewHolder>() {

    private val mDiffer: AsyncListDiffer<String> =
        AsyncListDiffer<String>(this, StickerPackDiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sticker_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mDiffer.currentList[position])
    }

    fun setData(stickersList: List<String>) {
        mDiffer.submitList(stickersList)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(image: String) {
            Glide.with(itemView.context).load(image).into(itemView.sticker_iv)
        }

    }

}