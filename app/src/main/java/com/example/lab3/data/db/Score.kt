package com.example.lab3.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val user: String,
    val score: Double,
    val time: Double
)