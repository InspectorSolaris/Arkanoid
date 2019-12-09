package com.example.lab3.view.scores

import com.example.lab3.presenter.scores.ScoresAdapter

interface ScoresActivityInterface {

    fun finish()

    fun setRecyclerView(
        scoresAdapter: ScoresAdapter,
        noScores: Boolean
    )

}