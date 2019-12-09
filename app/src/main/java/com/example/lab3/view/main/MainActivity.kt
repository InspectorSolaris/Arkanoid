package com.example.lab3.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab3.R
import com.example.lab3.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    MainActivityInterface {

    companion object {
        const val DIFFICULTY_STRING = "DIFFICULTY_STRING"
    }

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityPresenter = MainActivityPresenter(applicationContext, this)

        mainActivityPresenter.onCreate()

        startGame.setOnClickListener {
            mainActivityPresenter.onStartGameClick()
        }

        settings.setOnClickListener {
            mainActivityPresenter.onSettingsClick()
        }

        score.setOnClickListener {
            mainActivityPresenter.onScoresClick()
        }

        exit.setOnClickListener {
            mainActivityPresenter.onExitClick()
        }
    }

    override fun onPause() {
        mainActivityPresenter.onPause()

        super.onPause()
    }

    override fun onResume() {
        mainActivityPresenter.onResume()

        super.onResume()
    }

    override fun onDestroy() {
        mainActivityPresenter.onDestroy()

        super.onDestroy()
    }

    override fun startActivity(
        intent: Intent?
    ) {
        super.startActivity(intent)
    }

    override fun finish() {
        mainActivityPresenter.onFinish()

        super.finish()
    }

}
