package com.example.lab3.data.game.objects.movables

import com.example.lab3.data.game.objects.movables.rectangle.MovableRectangle
import com.example.lab3.data.game.objects.movables.rectangle.MovableRectangleInterface

class Paddle(
    minX: Double, minY: Double,
    maxX: Double, maxY: Double,
    vecX: Double, vecY: Double
) :
    MovableRectangleInterface by MovableRectangle(
        minX, minY,
        maxX, maxY,
        vecX, vecY
    )