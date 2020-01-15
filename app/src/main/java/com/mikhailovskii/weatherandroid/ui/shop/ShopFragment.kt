package com.mikhailovskii.weatherandroid.ui.shop


import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.StickerPack
import com.mikhailovskii.weatherandroid.ui.adapter.StickersAdapter
import com.mikhailovskii.weatherandroid.ui.sticker_purchase.StickerPurchaseActivity
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(), ShopContract.ShopView, StickersAdapter.OnItemClickListener {

    private val presenter = ShopPresenter()
    private var adapter: StickersAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(context!!, R.color.shopStatusBar)

        stickers_list.layoutManager = LinearLayoutManager(context)
        adapter = StickersAdapter(this)
        stickers_list.adapter = adapter
        stickers_list.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                ContextCompat.getColor(context!!, R.color.shopGradientTopLeft),
                ContextCompat.getColor(context!!, R.color.shopGradientBottomRight)
            )
        )

        stickers_list.background = gradientDrawable

        presenter.getStickerList()
    }

    override fun onStickerListLoaded(stickers: List<StickerPack>) {
        adapter?.setData(stickers)
    }

    override fun onStickerListFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }

    override fun onItemClicked(position: Int, item: StickerPack) {
        val intent = Intent(context, StickerPurchaseActivity::class.java)
        intent.putExtra(
            StickerPurchaseActivity.EXTRA_NAME,
            adapter?.stickersList?.get(position)?.title
        )
        startActivity(intent)
    }

}
