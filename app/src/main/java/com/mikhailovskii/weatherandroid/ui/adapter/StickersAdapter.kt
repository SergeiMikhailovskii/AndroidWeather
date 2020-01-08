package com.mikhailovskii.weatherandroid.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerElement
import kotlinx.android.synthetic.main.sticker_element.view.*

class StickersAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<StickersAdapter.ViewHolder>() {

    val stickersList = ArrayList<StickerElement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sticker_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stickersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(stickersList[position], onItemClickListener)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(stickersList: List<StickerElement>) {
        this.stickersList.clear()
        this.stickersList.addAll(stickersList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClicked(position: Int, item: StickerElement)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindData(element: StickerElement, onItemClickListener: OnItemClickListener) {
            Glide.with(itemView.context).load(element.image).into(itemView.sticker_iv)
            itemView.sticker_name_tv.text = element.title
            itemView.sticker_price_tv.text = "$ ${element.price}"

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(adapterPosition, element)
                }
            }
        }

    }

}