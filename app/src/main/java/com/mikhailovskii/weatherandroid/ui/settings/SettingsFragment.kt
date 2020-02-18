package com.mikhailovskii.weatherandroid.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mikhailovskii.weatherandroid.R
import com.mikhailovskii.weatherandroid.data.entities.User
import com.mikhailovskii.weatherandroid.util.showErrorToast
import com.mikhailovskii.weatherandroid.util.showSuccessToast
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.scope.currentScope

class SettingsFragment : Fragment(), SettingsContract.SettingsView {

    private val presenter by currentScope.inject<SettingsContract.SettingsPresenter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.settingsStatusBar)

        presenter.attachView(this)

        save_btn.setOnClickListener {
            val user = User()
            user.login = login_tiet.text.toString()
            user.password = change_password_tiet.text.toString()
            user.location = location_tiet.text.toString()

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

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onUserDataSaved() {
        showSuccessToast(getString(R.string.data_updated))
    }

    override fun onUserDataFailed() {
        showSuccessToast(getString(R.string.saving_failed))
    }

    override fun onInitialUserDataLoaded(user: User?) {
        location_tiet.setText(user?.location)
        login_tiet.setText(user?.login)
        change_password_tiet.setText(user?.password)

        if (user?.preferredUnit == 0) {
            celsius_rb.isChecked = true
        } else {
            fahrenheit_rb.isChecked = true
        }

    }

    override fun onInitialUserDataFailed() {
        showErrorToast(getString(R.string.loading_failed))
    }

}
