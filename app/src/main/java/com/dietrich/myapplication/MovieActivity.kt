package com.dietrich.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MovieActivity : AppCompatActivity() {
    private lateinit var movieNameField: TextView
    private lateinit var movieGenreField: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var voteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val extras = intent.extras!!

        movieNameField = findViewById(R.id.movieName)
        movieGenreField = findViewById(R.id.movieGenre)
        moviePoster = findViewById(R.id.moviePoster)
        voteButton = findViewById(R.id.voteButton)

        movieNameField.text = extras.getString("movieName")
        movieGenreField.text = extras.getString("movieGenre")
        Picasso.get().load(extras.getString("moviePosterUrl")).resize(256, 256).into(moviePoster)

        voteButton.isEnabled = !(State["votedMovie"] as Boolean)

        voteButton.setOnClickListener {
            State["votingForMovie"] = Movie(
                extras.getLong("movieId"),
                extras.getString("movieName")!!,
                extras.getString("movieGenre")!!,
                extras.getString("moviePosterUrl")!!
            )
            finish()
        }
    }
}