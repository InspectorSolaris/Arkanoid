package com.example.lab3.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {

    @Query("SELECT * FROM Score")
    fun getScores(): List<Score>

    @Insert
    fun insertScores(
        score: Score
    )

}