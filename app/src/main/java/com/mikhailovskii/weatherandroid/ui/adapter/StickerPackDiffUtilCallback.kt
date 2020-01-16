package com.mikhailovskii.weatherandroid.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class StickerPackDiffUtilCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = true

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

}