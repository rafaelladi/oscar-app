package com.dietrich.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameField = findViewById(R.id.email)
        passwordField = findViewById(R.id.password)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            if(usernameField.text.isEmpty() || passwordField.text.isEmpty()) {
                createToast("Preencha os dados necessarios!")
            }

            button.isEnabled = false
            val call = RetrofitConfig().loginAPI.login(LoginRequest(
                usernameField.text.toString(),
                passwordField.text.toString()
            ))

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    button.isEnabled = true
                    if(response.isSuccessful) {
                        val loginResponse = response.body()!!
                        State["userId"] = loginResponse.id
                        State["email"] = loginResponse.email
                        State["user"] = loginResponse.user
                        State["token"] = loginResponse.token
                        State["votedMovie"] = loginResponse.votedMovie
                        State["votedDirector"] = loginResponse.votedDirector
                        goToPage(HomeActivity::class.java)
                    } else {
                        createToast("Usuário e/ou senha errado(s)\nTente novamente")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    button.isEnabled = true
//                    val loginResponse = LoginResponse("test@email.com", "test", 100, false, false)
//                    State["email"] = loginResponse.email
//                    State["user"] = loginResponse.user
//                    State["token"] = loginResponse.token
//                    State["votedMovie"] = loginResponse.votedMovie
//                    State["votedDirector"] = loginResponse.votedDirector
//                    goToPage(HomeActivity::class.java)
                    createToast("Erro de comunicaçao com o servidor!\nTente novamente")
                }
            })
        }
    }
}

data class Image(
    val url: String,
    val name: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

class LoginResponse(
    val id: Long,
    val email: String,
    val user: String,
    val token: Int,
    val votedMovie: Boolean,
    val votedDirector: Boolean
)

fun <T> MutableList<T>.random(): T {
    val index = Random.nextInt(this.size)
    val elem = this[index]
    this.removeAt(index)
    println(elem)
    return elem
}

fun Context.createToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT).show()
}

fun Context.goToPage(pageClass: Class<out Context>, extras: Bundle = Bundle()) {
    val intent = Intent(this, pageClass)
    intent.putExtras(extras)
    startActivity(intent)
}