package com.mikhailovskii.weatherandroid.ui.settings


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.util.showSuccessToast
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(), SettingsContract.SettingsView {

    private val presenter = SettingsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(0xffa35043.toInt(), 0xffcd7d5c.toInt())
        )

        scrollView.background = gradientDrawable

        save_btn.setOnClickListener {
            val user = User()
            user.login = login_et.text.toString()
            user.password = change_password_et.text.toString()
            user.location = location_et.text.toString()

            val checkedRadioButtonId = units_rg.checkedRadioButtonId

            if (checkedRadioButtonId == R.id.celsius_rb) {
                user.preferredUnit = 0
            } else if (checkedRadioButtonId == R.id.fahrenheit_rb) {
                user.preferredUnit = 1
            }

            presenter.saveEditedUserInfo(user)
        }

        presenter.getInitialUserData()

    }

    override fun onUserDataSaved() {
        showSuccessToast("Data updated!")
    }

    override fun onUserDataFailed() {

    }

    override fun onInitialUserDataLoaded(user: User?) {
        location_et.setText(user?.location)
        login_et.setText(user?.login)
        change_password_et.setText(user?.password)

        if (user?.preferredUnit == 0) {
            celsius_rb.isChecked = true
        } else {
            fahrenheit_rb.isChecked = true
        }

    }

    override fun onInitialUserDataFailed() {

    }

    override fun showEmptyState(value: Boolean) {

    }

    override fun showLoadingIndicator(value: Boolean) {

    }


}
