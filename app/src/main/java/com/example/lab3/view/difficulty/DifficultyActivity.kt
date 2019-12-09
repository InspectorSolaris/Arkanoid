package com.example.lab3.view.difficulty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab3.R
import com.example.lab3.presenter.DifficultyActivityPresenter
import kotlinx.android.synthetic.main.activity_difficulty.*

class DifficultyActivity :
    AppCompatActivity(),
    DifficultyActivityInterface {

    private val difficultyActivityPresenter =
        DifficultyActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        difficultyActivityPresenter.onCreate()

        back.setOnClickListener {
            difficultyActivityPresenter.onBackClick()
        }

        easy.setOnClickListener {
            difficultyActivityPresenter.onEasyClick()
        }

        medium.setOnClickListener {
            difficultyActivityPresenter.onMediumClick()
        }

        hard.setOnClickListener {
            difficultyActivityPresenter.onHardClick()
        }
    }

    override fun onPause() {
        difficultyActivityPresenter.onPause()

        super.onPause()
    }

    override fun onResume() {
        difficultyActivityPresenter.onResume()

        super.onResume()
    }

    override fun onDestroy() {
        difficultyActivityPresenter.onDestroy()

        super.onDestroy()
    }

    override fun startActivity(
        intent: Intent?
    ) {
        super.startActivity(intent)
    }

    override fun finish() {
        difficultyActivityPresenter.onFinish()

        super.finish()
    }
}
