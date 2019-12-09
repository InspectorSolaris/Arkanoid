package com.example.lab3.presenter

import android.content.Context
import com.example.lab3.R
import com.example.lab3.data.managers.AudioManager
import com.example.lab3.view.settings.SettingsActivity
import com.example.lab3.view.settings.SettingsActivityInterface

class SettingsActivityPresenter(
    private var settingsActivity: SettingsActivityInterface?
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
        settingsActivity = null
    }

    fun onFinish() {
        anotherActivityStarted = true
    }

    fun onSet1Click() {
        val resources = (settingsActivity as SettingsActivity).resources
        val playlist = resources.getStringArray(R.array.standard_background)
        val audio = resources.getStringArray(R.array.standard_common)

        (settingsActivity as SettingsActivity).assets

        AudioManager.initData(
            settingsActivity as Context,
            playlist.toCollection(ArrayList()),
            audio.toCollection(ArrayList())
        )
        AudioManager.startPlaylist()
    }

    fun onSet2Click() {
        val resources = (settingsActivity as SettingsActivity).resources
        val playlist = resources.getStringArray(R.array.mlg_background)
        val audio = resources.getStringArray(R.array.mlg_common)

        AudioManager.initData(
            settingsActivity as Context,
            playlist.toCollection(ArrayList()),
            audio.toCollection(ArrayList())
        )
        AudioManager.startPlaylist()
    }

    fun onSet3Click() {
        val resources = (settingsActivity as SettingsActivity).resources
        val playlist = resources.getStringArray(R.array.thereisnowayback_background)
        val audio = resources.getStringArray(R.array.thereisnowayback_common)

        AudioManager.initData(
            settingsActivity as Context,
            playlist.toCollection(ArrayList()),
            audio.toCollection(ArrayList())
        )
        AudioManager.startPlaylist()
    }

    fun onBackClick() {
        settingsActivity?.finish()
    }

}