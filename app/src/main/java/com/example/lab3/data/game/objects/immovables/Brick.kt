package com.example.lab3.data.game.objects.immovables

import com.example.lab3.data.game.objects.immovables.rectangle.ImmovableRectangle
import com.example.lab3.data.game.objects.immovables.rectangle.ImmovableRectangleInterface

class Brick(
    minX: Double, minY: Double,
    maxX: Double, maxY: Double
) :
    ImmovableRectangleInterface by ImmovableRectangle(
        minX, minY,
        maxX, maxY
    )