package com.dietrich.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterMovies(val movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val listItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_list_movies, parent, false)

        return MovieViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.name.text = movie.name
        holder.genre.text = movie.genre
        Picasso.get().load(movie.posterUrl).resize(120, 120).into(holder.poster)
    }

    override fun getItemCount(): Int = movies.size
}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView
    val genre: TextView
    val poster: ImageView

    init {
        name = itemView.findViewById(R.id.textViewName)
        genre = itemView.findViewById(R.id.textViewGenre)
        poster = itemView.findViewById(R.id.posterImageView)
    }
}