package com.example.lab3.view.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab3.R
import com.example.lab3.presenter.SettingsActivityPresenter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity :
    AppCompatActivity(),
    SettingsActivityInterface {

    private val settingsActivityPresenter = SettingsActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsActivityPresenter.onCreate()

        set1.setOnClickListener {
            settingsActivityPresenter.onSet1Click()
        }

        set2.setOnClickListener {
            settingsActivityPresenter.onSet2Click()
        }

        set3.setOnClickListener {
            settingsActivityPresenter.onSet3Click()
        }

        back.setOnClickListener {
            settingsActivityPresenter.onBackClick()
        }
    }

    override fun onPause() {
        settingsActivityPresenter.onPause()

        super.onPause()
    }

    override fun onResume() {
        settingsActivityPresenter.onResume()

        super.onResume()
    }

    override fun onDestroy() {
        settingsActivityPresenter.onDestroy()

        super.onDestroy()
    }

    override fun finish() {
        settingsActivityPresenter.onFinish()

        super.finish()
    }

}
