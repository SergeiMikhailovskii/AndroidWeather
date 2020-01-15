package com.mikhailovskii.weatherandroid.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.util.showInfoToast
import kotlinx.android.synthetic.main.sticker_element.view.*

class StickersAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<StickersAdapter.ViewHolder>() {

    var stickersList: List<StickerPack> = ArrayList()

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

    fun setData(stickersList: List<StickerPack>) {
        val stickerDiffResult =
            DiffUtil.calculateDiff(StickersDiffUtilCallback(this.stickersList, stickersList))
        this.stickersList = stickersList
        stickerDiffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {

        fun onItemClicked(position: Int, item: StickerPack)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindData(element: StickerPack, onItemClickListener: OnItemClickListener) {

            if (element.stickers?.isNotEmpty()!!) {
                Glide.with(itemView.context).load(element.stickers?.get(0))
                    .into(itemView.sticker_iv)
            }

            itemView.sticker_name_tv.text = element.title
            itemView.sticker_price_tv.text = "$ ${element.price}"

            itemView.buy_btn.setOnClickListener {
                showInfoToast("Purchase succeed!")
            }

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(adapterPosition, element)
                }
            }
        }

    }

}