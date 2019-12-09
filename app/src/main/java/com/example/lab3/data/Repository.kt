package com.example.lab3.data

import com.example.lab3.data.db.Score
import com.example.lab3.data.db.ScoresDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Repository {

    const val DATABASE_NAME = "scoresDatabase"

    private lateinit var scoresDatabase: ScoresDatabase

    fun setScoresDatabase(
        scoresDatabase: ScoresDatabase
    ) {
        this.scoresDatabase = scoresDatabase
    }

    fun getScores(
        onGetScores: (scores: List<Score>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            onGetScores(
                withContext(Dispatchers.IO) {
                    scoresDatabase.scoreDao().getScores()
                }
            )
        }
    }

    fun insertScore(
        score: Score
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            scoresDatabase.scoreDao().insertScores(score)
        }
    }

}