package com.mikhailovskii.weatherandroid.ui.sticker_purchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mikhailovskii.weatherandroid.R

class StickerPurchaseActivity : AppCompatActivity(), StickerPurchaseContract.StickerPurchaseView {

    private val presenter = StickerPurchasePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticker_purchase)

        presenter.attachView(this)

        val name = intent.getStringExtra(EXTRA_NAME)


    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"

    }

}
