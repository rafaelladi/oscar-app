package com.dietrich.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmActivity : AppCompatActivity() {
    private lateinit var movieName: TextView
    private lateinit var directorName: TextView
    private lateinit var tokenInput: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        movieName = findViewById(R.id.confirmMovieName)
        directorName = findViewById(R.id.confirmDirectorName)
        tokenInput = findViewById(R.id.tokenInput)
        button = findViewById(R.id.confirmVote)

        val votedMovie = State["votedMovie"] as Boolean
        val votedDirector = State["votedDirector"] as Boolean
        val votingForMovie = State["votingForMovie"] as Movie?
        val votingForDirector = State["votingForDirector"] as Director?

        var movieId: Long? = null
        var directorId: Long? = null

        if(votedMovie) {
            movieId = null
            movieName.text = "Você já votou para filme"
        } else if(votingForMovie != null) {
            val movie = State["votingForMovie"] as Movie
            movieId = movie.id
            movieName.text = movie.name
        }

        if(votedDirector) {
            directorId = null
            directorName.text = "Você já votou para diretor"
        } else if(votingForDirector != null) {
            val director = State["votingForDirector"] as Director
            directorId = director.id
            directorName.text = director.name
        }

        button.isEnabled = movieId != null || directorId != null

        button.setOnClickListener {
            try {
                if(tokenInput.text.isEmpty() && tokenInput.text.contains(".")
                    || tokenInput.text.toString().toInt() > 100 || tokenInput.text.toString().toInt() < 0) {
                    createToast("Por favor, digite um token válido")
                } else {
                    val call = RetrofitConfig().loginAPI.vote(
                        "${State["userId"]!!}",
                        tokenInput.text.toString(),
                        movieId,
                        directorId
                    )

                    button.isEnabled = false

                    call.enqueue(object : Callback<VoteSuccess> {
                        override fun onResponse(call: Call<VoteSuccess>, response: Response<VoteSuccess>) {
                            if(response.isSuccessful) {
                                createToast("Votos salvos com sucesso")
                                if (movieId != null)
                                    State["votedMovie"] = true

                                if (directorId != null)
                                    State["votedDirector"] = true
                            } else if(response.code() == 400) {
                                createToast("Token inválido")
                                button.isEnabled = true
                            } else if(response.code() == 404) {
                                createToast("Filme ou diretor inválido")
                                button.isEnabled = true
                            }
                        }

                        override fun onFailure(call: Call<VoteSuccess>, t: Throwable) {
                            createToast("Erro de comunicaçao com o servidor")
                            button.isEnabled = true
                        }
                    })
                }
            } catch(e: Exception) {
                createToast("Por favor, digite um token válido")
            }
        }
    }
}

data class VoteSuccess(
    var movieId: Long? = null,
    var directorId: Long? = null
)