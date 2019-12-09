package com.example.lab3.presenter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.SurfaceHolder
import android.widget.EditText
import com.example.lab3.R
import com.example.lab3.data.Repository
import com.example.lab3.data.db.Score
import com.example.lab3.data.managers.AudioManager
import com.example.lab3.data.game.GameState
import com.example.lab3.data.geometry.rectangle.RectangleInterface
import com.example.lab3.data.sensors.GameSensorEventListener
import com.example.lab3.view.game.GameActivity
import com.example.lab3.view.game.GameActivityInterface
import kotlinx.coroutines.*

class GameActivityPresenter(
    private var gameActivity: GameActivityInterface?
) {

    private lateinit var alertDialogOnWin: AlertDialog
    private lateinit var alertDialogOnLose: AlertDialog

    private var anotherActivityStarted = false

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorGyroscope: Sensor
    private val gameSensorEventListener = GameSensorEventListener()

    private val frameTime = 1.0 / 60.0
    private var gameLoopIsRunning = false
    private lateinit var gameLoopJob: Job

    private lateinit var gameState: GameState

    private var textSize = 0.0
    private var marginLeft = 0.0
    private var marginTop = 0.0
    private var marginRight = 0.0
    private var marginBottom = 0.0

    fun onCreate() {
        initViewParameters()
        initSensorData()
        initGameState()
        initAlertDialogs()
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
        gameActivity = null
    }

    fun onFinish() {
        anotherActivityStarted = true
    }

    fun startGameLoop(
        surfaceHolder: SurfaceHolder
    ) {
        gameLoopIsRunning = true

        gameLoopJob = GlobalScope.launch(Dispatchers.Default) {
            while (gameLoopIsRunning) {
                val start = System.currentTimeMillis()

                gameState.input(1024.0 * gameSensorEventListener.normailzedAxisY.toDouble())
                gameState.update(frameTime)

                val canvas = surfaceHolder.lockCanvas()

                drawGameState(canvas)

                surfaceHolder.unlockCanvasAndPost(canvas)

                val end = System.currentTimeMillis()

                delay((1000 * frameTime).toLong() - (end - start))
            }
        }
    }

    fun stopGameLoop() {
        gameLoopIsRunning = false

        GlobalScope.launch(Dispatchers.Main) {
            gameLoopJob.join()
        }
    }

    private fun initViewParameters() {
        textSize = (gameActivity as GameActivity).resources.getInteger(R.integer.gameViewTextSize)
            .toDouble()

        val margins = (gameActivity as GameActivity).resources.getIntArray(R.array.gameViewMargins)

        marginLeft = margins[0].toDouble()
        marginTop = margins[1].toDouble()
        marginRight = margins[2].toDouble()
        marginBottom = margins[3].toDouble()
    }

    private fun initSensorData() {
        sensorManager = gameActivity?.getSensorManager()!!
        sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        sensorManager.registerListener(
            gameSensorEventListener,
            sensorGyroscope,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    private fun initGameState() {
        val difficulty = gameActivity?.getDifficulty()!!
        val displayMetrics = gameActivity?.getDisplayMetrics()!!

        val resources = (gameActivity as GameActivity).resources

        val screenWidth = displayMetrics.widthPixels.toDouble()
        val screenHeight = displayMetrics.heightPixels.toDouble()

        val bricksGap = resources.getIntArray(R.array.bricksGap)[difficulty].toDouble()
        val bricksPerColumn = resources.getIntArray(R.array.bricksPerColumn)[difficulty]
        val bricksPerRow = resources.getIntArray(R.array.bricksPerRow)[difficulty]
        val ballSpeed = resources.getIntArray(R.array.ballSpeed)[difficulty].toDouble()
        val ballSizeX = resources.getIntArray(R.array.ballSizeX)[difficulty].toDouble()
        val ballSizeY = resources.getIntArray(R.array.ballSizeY)[difficulty].toDouble()
        val paddleSizeX = resources.getIntArray(R.array.paddleSizeX)[difficulty].toDouble()
        val paddleSizeY = resources.getIntArray(R.array.paddleSizeY)[difficulty].toDouble()
        val lives = resources.getIntArray(R.array.lives)[difficulty]
        val scoreMultiplier = resources.getIntArray(R.array.scoreMultiplier)[difficulty].toDouble()

        gameState = GameState(
            marginLeft,
            marginTop,
            screenWidth - marginRight,
            screenHeight - marginBottom,
            bricksGap,
            bricksPerColumn,
            bricksPerRow,
            ballSpeed,
            ballSizeX,
            ballSizeY,
            paddleSizeX,
            paddleSizeY,
            lives,
            scoreMultiplier,
            object : GameState.GameStateListener {

                override fun onWinListener(score: Double) {
                    stopGameLoop()

                    AudioManager.startAudio(3)

                    GlobalScope.launch(Dispatchers.Main) {
                        alertDialogOnWin.show()
                    }
                }

                override fun onLoseListener(score: Double) {
                    stopGameLoop()

                    AudioManager.startAudio(2)

                    GlobalScope.launch(Dispatchers.Main) {
                        alertDialogOnLose.show()
                    }
                }

                override fun onHitPaddleListener() {
                    AudioManager.startAudio(0)
                }

                override fun onHitBrickListener() {
                    AudioManager.startAudio(1)
                }

            }
        )
    }

    private fun initAlertDialogs() {
        val resources = (gameActivity as GameActivity).resources

        val onWinTitle = resources.getString(R.string.game_you_win_title)
        val onLoseTitle = resources.getString(R.string.game_you_lose_title)
        val onWinMessage = resources.getString(R.string.game_you_win_message)

        val positiveButton = resources.getString(R.string.game_positive_button_string)
        val negativeButton = resources.getString(R.string.game_negative_button_string)

        alertDialogOnWin = AlertDialog.Builder(gameActivity as Context)
            .setTitle(onWinTitle)
            .setMessage(onWinMessage)
            .setCancelable(false)
            .setView(R.layout.score_input_user)
            .setPositiveButton(positiveButton) { dialogInterface: DialogInterface, _: Int ->
                val dialog = dialogInterface as Dialog
                val user = (dialog.findViewById(R.id.user) as EditText).text.toString()

                if (user.isNotEmpty()) {
                    Repository.insertScore(
                        Score(
                            0,
                            user,
                            gameState.score,
                            gameState.timer
                        )
                    )
                }

                gameActivity?.finish()
            }
            .setNegativeButton(negativeButton) { _: DialogInterface, _: Int ->
                gameActivity?.finish()
            }
            .create()

        alertDialogOnLose = AlertDialog.Builder(gameActivity as Context)
            .setTitle(onLoseTitle)
            .setCancelable(false)
            .setNegativeButton(negativeButton) { _: DialogInterface, _: Int ->
                gameActivity?.finish()
            }
            .create()
    }

    private fun drawGameState(
        canvas: Canvas
    ) {
        canvas.drawColor(Color.WHITE)

        val paint = Paint().also {
            it.color = Color.BLACK
            it.textSize = textSize.toFloat()
        }

        gameState.bricks.forEach {
            drawRectangle(canvas, paint, it)
        }

        drawRectangle(canvas, paint, gameState.paddle)
        drawRectangle(canvas, paint, gameState.ball)

        canvas.drawRect(
            gameState.bounds.minX.toFloat() + 0.0F,
            gameState.bounds.maxY.toFloat() + 0.0F,
            gameState.bounds.maxX.toFloat() + 0.0F,
            gameState.bounds.maxY.toFloat() + 4.0F,
            paint
        )

        val livesString = (gameActivity as GameActivity).getString(R.string.game_lives_string)
        val scoreString = (gameActivity as GameActivity).getString(R.string.game_score_string)

        canvas.drawText(
            "$livesString${gameState.lives}",
            16.0F,
            gameState.bounds.maxY.toFloat() + marginBottom.toFloat() - 8.0F,
            paint
        )

        canvas.drawText(
            "$scoreString${gameState.score}",
            16.0F,
            gameState.bounds.maxY.toFloat() + marginBottom.toFloat() - 16.0F - textSize.toFloat(),
            paint
        )
    }

    private fun drawRectangle(
        canvas: Canvas,
        paint: Paint,
        r: RectangleInterface
    ) {
        canvas.drawRect(
            r.toRect(),
            paint
        )
    }

}