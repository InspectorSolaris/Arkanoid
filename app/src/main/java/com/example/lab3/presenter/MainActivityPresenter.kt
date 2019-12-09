package com.example.lab3.presenter

import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.example.lab3.R
import com.example.lab3.data.Repository
import com.example.lab3.data.db.ScoresDatabase
import com.example.lab3.data.managers.AudioManager
import com.example.lab3.view.difficulty.DifficultyActivity
import com.example.lab3.view.main.MainActivity
import com.example.lab3.view.main.MainActivityInterface
import com.example.lab3.view.scores.ScoresActivity
import com.example.lab3.view.settings.SettingsActivity

class MainActivityPresenter(
    applicationContext: Context,
    private var mainActivity: MainActivityInterface?
) {

    private var anotherActivityStarted = false

    init {
        Repository.setScoresDatabase(
            Room.databaseBuilder(
                applicationContext,
                ScoresDatabase::class.java,
                Repository.DATABASE_NAME
            ).build()
        )
    }

    fun onCreate() {
        val playlist =
            (mainActivity as MainActivity).resources.getStringArray(R.array.standard_background)
                .toCollection(ArrayList())
        val audio =
            (mainActivity as MainActivity).resources.getStringArray(R.array.standard_common)
                .toCollection(ArrayList())

        AudioManager.initData(
            mainActivity as Context,
            playlist,
            audio
        )
        AudioManager.startPlaylist()
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
        AudioManager.stopAll()
        AudioManager.releaseAll()

        mainActivity = null
    }

    fun onFinish() {

    }

    fun onStartGameClick() {
        anotherActivityStarted = true
        mainActivity?.startActivity(
            Intent(
                mainActivity as Context,
                DifficultyActivity::class.java
            )
        )
    }

    fun onSettingsClick() {
        anotherActivityStarted = true
        mainActivity?.startActivity(
            Intent(
                mainActivity as Context,
                SettingsActivity::class.java
            )
        )
    }

    fun onScoresClick() {
        anotherActivityStarted = true
        mainActivity?.startActivity(
            Intent(
                mainActivity as Context,
                ScoresActivity::class.java
            )
        )
    }

    fun onExitClick() {
        mainActivity?.finish()
    }

}