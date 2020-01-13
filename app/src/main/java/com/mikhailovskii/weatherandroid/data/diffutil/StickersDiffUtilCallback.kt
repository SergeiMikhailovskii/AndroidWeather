package com.mikhailovskii.weatherandroid.data.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.mikhailovskii.weatherandroid.data.entities.StickerPack

class StickersDiffUtilCallback(
    private val oldList: List<StickerPack>,
    private val newList: List<StickerPack>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStickerElement = oldList[oldItemPosition]
        val newStickerElement = newList[newItemPosition]
        return oldStickerElement.title == newStickerElement.title
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStickerElement = oldList[oldItemPosition]
        val newStickerElement = newList[newItemPosition]
        return oldStickerElement.title == newStickerElement.title
                && oldStickerElement.stickers?.get(0)== newStickerElement.stickers?.get(0)
                && oldStickerElement.price == newStickerElement.price
    }

}