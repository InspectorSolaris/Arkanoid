package com.example.lab3.data.game.objects.movables.rectangle

import com.example.lab3.data.geometry.bounds.BoundsInterface
import com.example.lab3.data.geometry.ReverseCoordinate
import com.example.lab3.data.geometry.rectangle.RectangleInterface
import com.example.lab3.data.geometry.vector.VectorInterface

interface MovableRectangleInterface :
    RectangleInterface,
    VectorInterface {

    fun move(
        dt: Double,
        bounds: BoundsInterface
    )

    fun positionOf(
        r: RectangleInterface
    ): ReverseCoordinate

    fun positionOf(
        rs: List<RectangleInterface>
    ): List<ReverseCoordinate>

}