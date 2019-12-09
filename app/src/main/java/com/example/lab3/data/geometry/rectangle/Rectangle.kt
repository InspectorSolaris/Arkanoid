package com.example.lab3.data.geometry.rectangle

import android.graphics.RectF
import com.example.lab3.data.geometry.bounds.Bounds
import com.example.lab3.data.geometry.bounds.BoundsInterface

class Rectangle(
    minX: Double,
    minY: Double,
    maxX: Double,
    maxY: Double
) :
    RectangleInterface,
    BoundsInterface by Bounds(
        minX, minY,
        maxX, maxY
    ) {

    override fun intersectWith(
        r: RectangleInterface
    ): Boolean {
        return isInsideRectangle(r.minX, r.minY) ||
                isInsideRectangle(r.minX, r.maxY) ||
                isInsideRectangle(r.maxX, r.minY) ||
                isInsideRectangle(r.maxX, r.maxY) ||
                isInsideRectangle(r)
    }

    override fun intersectWith(
        rs: List<RectangleInterface>
    ): List<Int> {
        val result = arrayListOf<Int>()

        for (i in rs.indices) {
            if (this.intersectWith(rs[i])) {
                result.add(i)
            }
        }

        return result
    }

    override fun toRect(): RectF {
        return RectF(
            minX.toFloat(), minY.toFloat(),
            maxX.toFloat(), maxY.toFloat()
        )
    }

    private fun isInsideRectangle(
        x: Double,
        y: Double
    ): Boolean {
        return x in minX..maxX &&
                y in minY..maxY
    }

    private fun isInsideRectangle(
        r: RectangleInterface
    ): Boolean {
        val rangeX = r.minX..r.maxX
        val rangeY = r.minY..r.maxY

        return minX in rangeX &&
                maxX in rangeX &&
                (minY in rangeY || maxY in rangeY) ||
                minY in rangeY &&
                maxY in rangeY &&
                (minX in rangeX || maxX in rangeX)
    }

}