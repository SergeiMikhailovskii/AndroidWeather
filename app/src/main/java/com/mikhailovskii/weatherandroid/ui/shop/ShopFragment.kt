package com.mikhailovskii.weatherandroid.ui.shop


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mikhailovskii.weatherandroid.R
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment(), ShopContract.ShopView {

    private val presenter = ShopPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xff3f5fa4.toInt(), 0xff17253e.toInt())
        )

        stickers_list.background = gradientDrawable
    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }


}
