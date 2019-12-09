package com.example.lab3.view.scores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.R
import com.example.lab3.presenter.scores.ScoresActivityPresenter
import com.example.lab3.presenter.scores.ScoresAdapter
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity :
    AppCompatActivity(),
    ScoresActivityInterface {

    private val scoresActivityPresenter = ScoresActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        scoresActivityPresenter.onCreate()

        back.setOnClickListener {
            scoresActivityPresenter.onBackClick()
        }
    }

    override fun onPause() {
        scoresActivityPresenter.onPause()

        super.onPause()
    }

    override fun onResume() {
        scoresActivityPresenter.onResume()

        super.onResume()
    }

    override fun onDestroy() {
        scoresActivityPresenter.onDestroy()

        super.onDestroy()
    }

    override fun finish() {
        scoresActivityPresenter.onFinish()

        super.finish()
    }

    override fun setRecyclerView(
        scoresAdapter: ScoresAdapter,
        noScores: Boolean
    ) {
        val context = this

        scoresRecyclerView.also {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = scoresAdapter
        }

        if (noScores) {
            this.noScores.visibility = View.VISIBLE
        } else {
            this.noScores.visibility = View.GONE
        }
    }

}
