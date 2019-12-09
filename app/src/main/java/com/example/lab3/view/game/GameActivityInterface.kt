package com.example.lab3.view.game

import android.hardware.SensorManager
import android.util.DisplayMetrics

interface GameActivityInterface {

    fun getSensorManager(): SensorManager

    fun getDifficulty(): Int

    fun getDisplayMetrics(): DisplayMetrics

    fun finish()

}