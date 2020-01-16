package com.mikhailovskii.weatherandroid.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mikhailovskii.weatherandroid.data.entities.StickerPack

class StickersDiffUtilCallback : DiffUtil.ItemCallback<StickerPack>() {

    override fun areItemsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean = true

    override fun areContentsTheSame(oldItem: StickerPack, newItem: StickerPack): Boolean {
        return oldItem.title == newItem.title
                && oldItem.stickers?.get(0) == newItem.stickers?.get(0)
                && oldItem.price == newItem.price
    }

}