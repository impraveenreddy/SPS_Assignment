package com.sps.spsassignment

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var backdropImageView: ImageView
    private lateinit var apiService: ApiService
    private lateinit var relatedMoviesRecyclerView: RecyclerView
    private lateinit var relatedMoviesAdapter: ListMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        titleTextView = findViewById(R.id.titleTextView)
        overviewTextView = findViewById(R.id.overviewTextView)
        backdropImageView = findViewById(R.id.backdropImageView)
        apiService = ApiClient.apiService
        val movie = intent.getParcelableExtra<Movie>("movie")
        if (movie != null) {
            setupUI(movie)
            fetchRelatedMovies(movie.id)
        }
    }

    private fun setupUI(movie: Movie) {
        titleTextView.text = movie.title
        overviewTextView.text = movie.overview
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
            .into(backdropImageView)

        relatedMoviesRecyclerView = findViewById(R.id.relatedMoviesRecyclerView)
        relatedMoviesAdapter = ListMovieAdapter(emptyList(), true) { clickedMovie ->
            // Handle click
        }
        relatedMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        relatedMoviesRecyclerView.adapter = relatedMoviesAdapter
    }

    private fun fetchRelatedMovies(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getRelatedMovies("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGUxYjhlMTEyZWYxOTFlNTJkZjUzMmU2MDEyNTc3YSIsInN1YiI6IjY1ODE3ZDk4ZGY4NmE4MDkzN2U3ZGRlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yp6UnW-cAnTz0QnXsKoJbIFA4JM_CO6SfoYKpXLP1t0", movieId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val relatedMovies = response.body()?.results ?: emptyList()
                    relatedMoviesAdapter.updateData(relatedMovies, true)
                } else {
                    // Handle error
                }
            }
        }
    }
}

