package com.example.lab3.presenter.scores

import com.example.lab3.data.Repository
import com.example.lab3.data.managers.AudioManager
import com.example.lab3.view.scores.ScoresActivityInterface

class ScoresActivityPresenter(
    private var scoresActivity: ScoresActivityInterface?
) {

    private var anotherActivityStarted = false

    fun onCreate() {
        Repository.getScores {
            val scores = it.sortedWith(
                compareBy(
                    { score -> -score.score },
                    { score -> -score.time },
                    { score -> score.user }
                )
            )

            scoresActivity?.setRecyclerView(
                ScoresAdapter(scores),
                scores.isEmpty()
            )
        }
    }

    fun onPause() {
        if (!anotherActivityStarted) {
            AudioManager.pausePlaylist()
        }
    }

    fun onResume() {
        if (!anotherActivityStarted) {
            AudioManager.resumePlaylist()
        }

        anotherActivityStarted = false
    }

    fun onDestroy() {
        scoresActivity = null
    }

    fun onFinish() {
        anotherActivityStarted = true
    }

    fun onBackClick() {
        scoresActivity?.finish()
    }

}