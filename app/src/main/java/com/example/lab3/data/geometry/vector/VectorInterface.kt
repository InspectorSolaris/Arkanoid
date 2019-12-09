package com.example.lab3.data.geometry.vector

interface VectorInterface {

    var vecX: Double
    var vecY: Double

    fun reverseVecX()

    fun reverseVecY()

    fun setX(
        x: Double
    )

    fun setY(
        y: Double
    )

    fun addX(
        x: Double
    )

    fun addY(
        y: Double
    )

    fun addVec(
        vec: VectorInterface
    )

}