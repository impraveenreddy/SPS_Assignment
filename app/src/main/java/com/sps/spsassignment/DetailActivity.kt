package com.sps.spsassignment

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var backdropImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        titleTextView = findViewById(R.id.titleTextView)
        overviewTextView = findViewById(R.id.overviewTextView)
        backdropImageView = findViewById(R.id.backdropImageView)

        val movie = intent.getParcelableExtra<Movie>("movie")
        if (movie != null) {
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview

            Picasso.get().load("https://image.tmdb.org/t/p/w500/${movie.backdrop_path}")
                .into(backdropImageView)
        }
    }
}
