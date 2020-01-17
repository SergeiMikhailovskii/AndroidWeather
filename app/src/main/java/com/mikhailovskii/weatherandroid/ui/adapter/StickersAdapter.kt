package com.mikhailovskii.weatherandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.util.showSuccessToast
import kotlinx.android.synthetic.main.sticker_element.view.*

class StickersAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<StickersAdapter.ViewHolder>() {

    val differ = AsyncListDiffer<StickerPack>(this, StickersDiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sticker_element, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(differ.currentList[position], onItemClickListener)
    }

    fun setData(stickersList: List<StickerPack>) {
        differ.submitList(stickersList)
    }

    interface OnItemClickListener {

        fun onItemClicked(position: Int, item: StickerPack)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(element: StickerPack, onItemClickListener: OnItemClickListener) {

            if (element.stickers?.isNotEmpty()!!) {
                Glide.with(itemView.context).load(element.stickers?.get(0))
                    .into(itemView.sticker_iv)
            }

            itemView.sticker_name_tv.text = element.title
            itemView.sticker_price_tv.text =
                itemView.resources.getString(R.string.show_price_in_usd, element.price)

            itemView.buy_btn.setOnClickListener {
                showSuccessToast("Purchase succeed!")
            }

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClicked(adapterPosition, element)
                }
            }
        }

    }

}