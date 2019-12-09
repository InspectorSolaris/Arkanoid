package com.example.lab3.presenter.scores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R
import com.example.lab3.data.db.Score
import kotlinx.android.synthetic.main.score_item.view.*

class ScoresAdapter(
    private val scores: List<Score>
) :
    RecyclerView.Adapter<ScoresAdapter.ScoreView>() {

    class ScoreView(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val user: TextView = view.user
        val score: TextView = view.score
        val time: TextView = view.time

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScoreView {
        return ScoreView(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.score_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(
        holder: ScoreView,
        position: Int
    ) {
        holder.user.text = scores[position].user
        holder.score.text = scores[position].score.toString()
        holder.time.text = scores[position].time.toString()
    }

}