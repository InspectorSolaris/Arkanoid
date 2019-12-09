package com.example.lab3.data.game.objects.immovables.rectangle

import com.example.lab3.data.geometry.rectangle.Rectangle
import com.example.lab3.data.geometry.rectangle.RectangleInterface

class ImmovableRectangle(
    minX: Double, minY: Double,
    maxX: Double, maxY: Double
) :
    ImmovableRectangleInterface,
    RectangleInterface by Rectangle(
        minX, minY,
        maxX, maxY
    )