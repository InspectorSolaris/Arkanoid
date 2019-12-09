package com.example.lab3.data.geometry.rectangle

import android.graphics.RectF
import com.example.lab3.data.geometry.bounds.BoundsInterface

interface RectangleInterface :
    BoundsInterface {

    fun intersectWith(
        r: RectangleInterface
    ): Boolean

    fun intersectWith(
        rs: List<RectangleInterface>
    ): List<Int>

    fun toRect(): RectF

}