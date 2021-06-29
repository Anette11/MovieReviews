package com.example.testtaskmoviereview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskmoviereview.databinding.OneItemLayoutBinding
import com.squareup.picasso.Picasso

class MovieRecyclerViewAdapter : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>() {
    private var list: List<Result> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val oneItemLayoutBinding = OneItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(oneItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val result = list[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = list.size

    class MovieViewHolder(private val oneItemLayoutBinding: OneItemLayoutBinding) :
        RecyclerView.ViewHolder(oneItemLayoutBinding.root) {

        fun bind(result: Result) = with(oneItemLayoutBinding) {

            Picasso.get()
                .load(result.multimedia.src)
                .error(R.drawable.ic_movie)
                .placeholder(R.drawable.ic_movie)
                .into(imageView)

            textViewName.text = result.display_title
            textViewDescription.text = result.summary_short

            constraintLayout.setOnClickListener {
                showMoreOrLessTextLines(textViewName)
                showMoreOrLessTextLines(textViewDescription)
            }
        }

        private fun showMoreOrLessTextLines(textView: TextView) {
            if (textView.maxLines == 1) textView.maxLines = Int.MAX_VALUE else textView.maxLines = 1
        }
    }

    fun updateList(updatedList: List<Result>) {
        this.list = updatedList
        notifyDataSetChanged()
    }
}