package com.mikhailovskii.weatherandroid.ui.shop


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerElement
import com.mikhailovskii.weatherandroid.ui.adapter.StickersAdapter
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(), ShopContract.ShopView {

    private val presenter = ShopPresenter()
    private var adapter: StickersAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.attachView(this)
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stickers_list.layoutManager = LinearLayoutManager(context)
        adapter = StickersAdapter()
        stickers_list.adapter = adapter

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xff3f5fa4.toInt(), 0xff17253e.toInt())
        )

        stickers_list.background = gradientDrawable
    }

    override fun onStickerListLoaded(stickers: List<StickerElement>) {

    }

    override fun onStickerListFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }


}
