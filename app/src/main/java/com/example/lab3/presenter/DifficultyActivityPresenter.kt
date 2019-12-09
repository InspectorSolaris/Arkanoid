package com.example.lab3.presenter

import android.content.Context
import android.content.Intent
import com.example.lab3.R
import com.example.lab3.data.managers.AudioManager
import com.example.lab3.view.difficulty.DifficultyActivity
import com.example.lab3.view.difficulty.DifficultyActivityInterface
import com.example.lab3.view.game.GameActivity
import com.example.lab3.view.main.MainActivity.Companion.DIFFICULTY_STRING

class DifficultyActivityPresenter(
    private var difficultyActivity: DifficultyActivityInterface?
) {

    private var anotherActivityStarted = false

    fun onCreate() {

    }

    fun onPause() {
        if (!anotherActivityStarted) {
            AudioManager.pausePlaylist()
        }
    }

    fun onResume() {
        if (!anotherActivityStarted) {
            AudioManager.resumePlaylist()
        }

        anotherActivityStarted = false
    }

    fun onDestroy() {
        difficultyActivity = null
    }

    fun onFinish() {
        anotherActivityStarted = true
    }

    fun onEasyClick() {
        anotherActivityStarted = true

        val difficultyId =
            (difficultyActivity as DifficultyActivity).resources.getIntArray(R.array.difficultyId)[0]

        difficultyActivity?.startActivity(
            Intent(
                difficultyActivity as Context,
                GameActivity::class.java
            ).also {
                it.putExtra(
                    DIFFICULTY_STRING,
                    difficultyId
                )
            }
        )
    }

    fun onMediumClick() {
        anotherActivityStarted = true

        val difficultyId =
            (difficultyActivity as DifficultyActivity).resources.getIntArray(R.array.difficultyId)[1]

        difficultyActivity?.startActivity(
            Intent(
                difficultyActivity as Context,
                GameActivity::class.java
            ).also {
                it.putExtra(
                    DIFFICULTY_STRING,
                    difficultyId
                )
            }
        )
    }

    fun onHardClick() {
        anotherActivityStarted = true

        val difficultyId =
            (difficultyActivity as DifficultyActivity).resources.getIntArray(R.array.difficultyId)[2]

        difficultyActivity?.startActivity(
            Intent(
                difficultyActivity as Context,
                GameActivity::class.java
            ).also {
                it.putExtra(
                    DIFFICULTY_STRING,
                    difficultyId
                )
            }
        )
    }

    fun onBackClick() {
        anotherActivityStarted = true
        difficultyActivity?.finish()
    }

}