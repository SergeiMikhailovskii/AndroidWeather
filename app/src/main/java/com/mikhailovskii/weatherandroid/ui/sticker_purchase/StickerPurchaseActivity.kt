package com.mikhailovskii.weatherandroid.ui.sticker_purchase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.adapter.StickerPackAdapter
import kotlinx.android.synthetic.main.activity_sticker_purchase.*

class StickerPurchaseActivity : AppCompatActivity(), StickerPurchaseContract.StickerPurchaseView {

    private val presenter = StickerPurchasePresenter()
    private var adapter: StickerPackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker_purchase)

        presenter.attachView(this)

        stickers_grid.layoutManager = GridLayoutManager(applicationContext, 2)
        adapter = StickerPackAdapter()
        stickers_grid.adapter = adapter

        val name = intent.getStringExtra(EXTRA_NAME)

        presenter.getStickerPackByName(name!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onStickerPackByNameLoaded(stickerPack: StickerPack) {
        val stickersList = ArrayList<String>()

        for (sticker in stickerPack.stickers!!) {
            stickersList.add(sticker)
        }

        adapter?.setData(stickersList)
        title_tv.text = stickerPack.title
        buy_btn.text = "${stickerPack.price} $"

        Glide.with(applicationContext).load(stickersList[0]).into(sticker_iv)
    }

    override fun onStickerPackByNameFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"

    }

}
