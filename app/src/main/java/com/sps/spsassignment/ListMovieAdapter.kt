package com.sps.spsassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ListMovieAdapter(
    private var movies: List<Movie>,
    private var details: Boolean,
    private val onItemClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        holder.textViewTitle.text = movie.title

        Picasso.get().load("https://image.tmdb.org/t/p/w200${movie.poster_path}")
            .into(holder.imageViewPoster)
        holder.itemView.setOnClickListener { onItemClick(movie) }
    }

    fun updateData(newMovies: List<Movie>, details: Boolean) {
        this.details = details;
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

