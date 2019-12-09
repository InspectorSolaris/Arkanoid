package com.example.lab3.data.geometry.bounds

class Bounds(
    override var minX: Double,
    override var minY: Double,
    override var maxX: Double,
    override var maxY: Double
) :
    BoundsInterface {

    override val width: Double
        get() = (maxX - minX)
    override val height: Double
        get() = (maxY - minY)
    override val centerX: Double
        get() = (minX + maxX) / 2.0
    override val centerY: Double
        get() = (minY + maxY) / 2.0

}