package com.example.lab3.data.geometry.vector

class Vector(
    override var vecX: Double,
    override var vecY: Double
) :
    VectorInterface {

    override fun reverseVecX() {
        vecX = -vecX
    }

    override fun reverseVecY() {
        vecY = -vecY
    }

    override fun setX(
        x: Double
    ) {
        vecX = x
    }

    override fun setY(
        y: Double
    ) {
        vecY = y
    }

    override fun addX(
        x: Double
    ) {
        vecX += x
    }

    override fun addY(
        y: Double
    ) {
        vecY += y
    }

    override fun addVec(
        vec: VectorInterface
    ) {
        addX(vec.vecX)
        addY(vec.vecY)
    }

}