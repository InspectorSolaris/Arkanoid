package com.example.lab3.data.game

import com.example.lab3.data.game.objects.movables.Ball
import com.example.lab3.data.game.objects.immovables.Brick
import com.example.lab3.data.game.objects.movables.Paddle
import com.example.lab3.data.geometry.ReverseCoordinate
import com.example.lab3.data.geometry.bounds.Bounds

class GameState(
    fieldMinX: Double,
    fieldMinY: Double,
    fieldMaxX: Double,
    fieldMaxY: Double,
    brickSpace: Double,
    bricksPerColumn: Int,
    bricksPerRow: Int,
    ballSpeed: Double,
    ballWidth: Double,
    ballHeight: Double,
    paddleWidth: Double,
    paddleHeight: Double,
    var lives: Int,
    private val scoreMultiplier: Double,
    private val gameStateListener: GameStateListener
) {

    interface GameStateListener {

        fun onWinListener(
            score: Double
        )

        fun onLoseListener(
            score: Double
        )

        fun onHitPaddleListener()

        fun onHitBrickListener()

    }

    var score = 0.0
    var timer = 0.0

    val bounds = Bounds(
        fieldMinX,
        fieldMinY,
        fieldMaxX,
        fieldMaxY
    )

    private val brickWidth: Double = (bounds.width / 1.0 - brickSpace) / bricksPerRow - brickSpace
    private val brickHeight: Double =
        (bounds.height / 2.0 - brickSpace) / bricksPerColumn - brickSpace

    lateinit var bricks: ArrayList<Brick>
    lateinit var ball: Ball
    lateinit var paddle: Paddle

    init {
        initBricks(
            bricksPerColumn,
            bricksPerRow,
            brickSpace
        )

        initBall(
            ballWidth,
            ballHeight,
            0.0,
            ballSpeed
        )

        initPaddle(
            paddleWidth,
            paddleHeight
        )
    }

    fun update(
        dt: Double
    ) {
        timer += dt

        ball.move(dt, bounds)
        paddle.move(dt, bounds)

        checkBounds()
        checkIntersections()
    }

    fun input(
        x: Double
    ) {
        paddle.setX(
            x + paddle.vecX / 2.5
        )
    }

    private fun initBricks(
        bricksPerColumn: Int,
        bricksPerRow: Int,
        brickSpace: Double
    ) {
        bricks = arrayListOf()

        for (i in 0 until bricksPerColumn) {
            for (j in 0 until bricksPerRow) {
                val startX = bounds.minX + (j + 1) * brickSpace + (j + 0) * brickWidth
                val startY = bounds.minY + (i + 1) * brickSpace + (i + 0) * brickHeight

                bricks.add(
                    Brick(
                        startX + 0,
                        startY + 0,
                        startX + brickWidth,
                        startY + brickHeight
                    )
                )
            }
        }
    }

    private fun initBall(
        width: Double,
        height: Double,
        vecX: Double = 0.0,
        vecY: Double = 512.0
    ) {
        val fieldWidth = bounds.maxX - bounds.minX
        val fieldHeight = bounds.maxY - bounds.minY

        val centerX = bounds.minX + fieldWidth / 2.0
        val centerY = bounds.minY + fieldHeight / 3.0 * 2.0

        ball = Ball(
            centerX - width / 2.0,
            centerY - height / 2.0,
            centerX + width / 2.0,
            centerY + height / 2.0,
            vecX,
            vecY
        )
    }

    private fun initPaddle(
        width: Double,
        height: Double,
        vecX: Double = 0.0,
        vecY: Double = 0.0
    ) {
        val fieldWidth = bounds.maxX - bounds.minX
        val fieldHeight = bounds.maxY - bounds.minY

        val centerX = bounds.minX + fieldWidth / 2.0
        val centerY = bounds.minY + fieldHeight - height / 2.0

        paddle = Paddle(
            centerX - width / 2.0,
            centerY - height / 2.0,
            centerX + width / 2.0,
            centerY + height / 2.0,
            vecX,
            vecY
        )
    }

    private fun checkBounds() {
        if (ball.minX == bounds.minX ||
            ball.maxX == bounds.maxX
        ) {
            ball.reverseVecX()
        } else if (ball.minY == bounds.minY) {
            ball.reverseVecY()
        } else if (ball.maxY == bounds.maxY) {
            --lives

            initBall(
                ball.width,
                ball.height,
                0.0,
                ball.vecY
            )

            initPaddle(
                paddle.width,
                paddle.height
            )

            if (lives == 0) {
                gameStateListener.onLoseListener(score)
            }
        }
    }

    private fun checkIntersections() {
        val intersectWithBrick = ball.intersectWith(bricks)

        if (ball.intersectWith(paddle)) {
            gameStateListener.onHitPaddleListener()

            when (ball.positionOf(paddle)) {
                ReverseCoordinate.X -> {
                    ball.reverseVecX()
                }
                ReverseCoordinate.Y -> {
                    ball.reverseVecY()
                }
                else -> {

                }
            }

            ball.addX(paddle.vecX / 2.0)
        } else if (intersectWithBrick.isNotEmpty()) {
            gameStateListener.onHitBrickListener()

            val intersectedBricks = arrayListOf<Brick>()

            intersectWithBrick.forEach { intersectedBricks.add(bricks[it]) }

            val positionOfBrick = ball.positionOf(intersectedBricks)

            if (positionOfBrick.contains(ReverseCoordinate.X)) {
                ball.reverseVecX()
            }
            if (positionOfBrick.contains(ReverseCoordinate.Y)) {
                ball.reverseVecY()
            }

            score += intersectWithBrick.size * scoreMultiplier
            intersectedBricks.forEach { bricks.remove(it) }

            if (bricks.isEmpty()) {
                gameStateListener.onWinListener(score)
            }
        }
    }

}