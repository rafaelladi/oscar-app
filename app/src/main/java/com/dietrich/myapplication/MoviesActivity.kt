package com.dietrich.myapplication

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {
    private lateinit var recyclerViewMovies: RecyclerView
    private var movies: List<Movie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies)

        loadMovies()
    }

    private fun updateMovies() {
        val adapter = AdapterMovies(movies)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewMovies.layoutManager = layoutManager

        recyclerViewMovies.setHasFixedSize(true)

        recyclerViewMovies.addItemDecoration(
            DividerItemDecoration(this, LinearLayout.VERTICAL)
        )

        recyclerViewMovies.adapter = adapter

        recyclerViewMovies.addOnItemTouchListener(RecyclerItemClickListener(
            applicationContext,
            recyclerViewMovies,
            object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val movie = movies[position]
                    val bundle = bundleOf(
                        "movieId" to movie.id,
                        "movieName" to movie.name,
                        "movieGenre" to movie.genre,
                        "moviePosterUrl" to movie.posterUrl
                    )
                    goToPage(MovieActivity::class.java, bundle)
                }

                override fun onLongItemClick(view: View, position: Int) {

                }

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }
            }
        ))
    }

    private fun loadMovies() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Carregando filmes...")
        progressDialog.show()

        val call = RetrofitConfig().loginAPI.getMovies()

        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if(response.isSuccessful) {
                    movies = response.body()!!
                    updateMovies()
                } else {
                    createToast("Erro de comunicaçao com o servidor!\nPor favor tente novamente")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                createToast("Erro de comunicaçao com o servidor!\nPor favor tente novamente")
                progressDialog.dismiss()
            }
        })
    }
}