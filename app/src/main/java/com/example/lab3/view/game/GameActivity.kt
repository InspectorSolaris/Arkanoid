package com.example.lab3.view.game

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.lab3.presenter.GameActivityPresenter
import com.example.lab3.view.main.MainActivity

class GameActivity :
    AppCompatActivity(),
    GameActivityInterface {

    private val gameActivityPresenter =
        GameActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameActivityPresenter.onCreate()

        setContentView(GameSurfaceView(this, gameActivityPresenter))
    }

    override fun onPause() {
        gameActivityPresenter.onPause()

        super.onPause()
    }

    override fun onResume() {
        gameActivityPresenter.onResume()

        super.onResume()
    }

    override fun onDestroy() {
        gameActivityPresenter.onDestroy()

        super.onDestroy()
    }

    override fun finish() {
        gameActivityPresenter.onFinish()

        super.finish()
    }

    override fun getSensorManager(): SensorManager {
        return getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun getDifficulty(): Int {
        return intent.getIntExtra(MainActivity.DIFFICULTY_STRING, -1)
    }

    override fun getDisplayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics
    }

    class GameSurfaceView(
        context: Context,
        private val gameActivityPresenter: GameActivityPresenter
    ) :
        SurfaceView(context),
        SurfaceHolder.Callback {

        init {
            holder.addCallback(this)
        }

        override fun surfaceChanged(
            p0: SurfaceHolder?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {

        }

        override fun surfaceDestroyed(
            p0: SurfaceHolder?
        ) {
            gameActivityPresenter.stopGameLoop()
        }

        override fun surfaceCreated(
            p0: SurfaceHolder?
        ) {
            gameActivityPresenter.startGameLoop(holder)
        }

    }

}
