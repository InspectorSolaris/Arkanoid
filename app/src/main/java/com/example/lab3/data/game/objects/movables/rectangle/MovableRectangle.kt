package com.example.lab3.data.game.objects.movables.rectangle

import com.example.lab3.data.geometry.bounds.BoundsInterface
import com.example.lab3.data.geometry.ReverseCoordinate
import com.example.lab3.data.geometry.rectangle.Rectangle
import com.example.lab3.data.geometry.rectangle.RectangleInterface
import com.example.lab3.data.geometry.vector.Vector
import com.example.lab3.data.geometry.vector.VectorInterface
import kotlin.math.max
import kotlin.math.min

class MovableRectangle(
    minX: Double, minY: Double,
    maxX: Double, maxY: Double,
    vecX: Double, vecY: Double
) :
    MovableRectangleInterface,
    RectangleInterface by Rectangle(
        minX, minY,
        maxX, maxY
    ),
    VectorInterface by Vector(
        vecX, vecY
    ) {

    override fun move(
        dt: Double,
        bounds: BoundsInterface
    ) {
        val dx = min(max(vecX * dt, bounds.minX - minX), bounds.maxX - maxX)
        val dy = min(max(vecY * dt, bounds.minY - minY), bounds.maxY - maxY)

        minX += dx
        minY += dy
        maxX += dx
        maxY += dy
    }

    override fun positionOf(
        r: RectangleInterface
    ): ReverseCoordinate {
        val rangeX = r.minX..r.maxX
        val rangeY = r.minY..r.maxY
        val extRangeX = (r.minX - width / 2)..(r.maxX + width / 2)
        val extRangeY = (r.minY - height / 2)..(r.maxY + height / 2)

        val rx = if (r.centerX < centerX) r.maxX else r.minX
        val ry = if (r.centerY < centerY) r.maxY else r.minY

        val rootX = (ry - centerY) / vecY * vecX + centerX
        val rootY = (rx - centerX) / vecX * vecY + centerY

        if (rootX.isFinite() &&
            rootY.isFinite() &&
            rootX in rangeX &&
            rootY in rangeY
        ) {
            if (centerX in rangeX) {
                return ReverseCoordinate.Y
            }
            if (centerY in rangeY) {
                return ReverseCoordinate.X
            }
        }

        if (rootX.isFinite() &&
            rootX in rangeX
        ) {
            return ReverseCoordinate.Y
        }

        if (rootY.isFinite() &&
            rootY in rangeY
        ) {
            return ReverseCoordinate.X
        }

        if (rootX.isFinite() &&
            rootX in extRangeX
        ) {
            return ReverseCoordinate.Y
        }

        if (rootY.isFinite() &&
            rootY in extRangeY
        ) {
            return ReverseCoordinate.X
        }

        return ReverseCoordinate.ERROR
    }

    override fun positionOf(
        rs: List<RectangleInterface>
    ): List<ReverseCoordinate> {
        val result = arrayListOf<ReverseCoordinate>()

        rs.forEach { result.add(this.positionOf(it)) }

        return result
    }

}