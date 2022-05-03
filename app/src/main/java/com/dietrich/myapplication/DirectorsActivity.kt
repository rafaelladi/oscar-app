package com.dietrich.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DirectorsActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var button: Button

    private var directors: List<Director> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directors)

        radioGroup = findViewById(R.id.radioGroup)
        button = findViewById(R.id.voteDirector)

        button.isEnabled = !(State["votedDirector"] as Boolean)

        loadDirectors()

        button.setOnClickListener {
            if(radioGroup.checkedRadioButtonId != -1) {
                State["votingForDirector"] = directors.find {
                    it.id.toInt() == radioGroup.checkedRadioButtonId
                }!!
                finish()
            }
        }
    }

    private fun updateDirectors() {
        directors.forEach {
            val radioButton = RadioButton(this)
            radioButton.text = it.name
            radioButton.id = it.id.toInt()
            radioGroup.addView(radioButton)
        }
    }

    private fun loadDirectors() {
        val call = RetrofitConfig().loginAPI.getDirectors()

        call.enqueue(object : Callback<List<Director>> {
            override fun onResponse(call: Call<List<Director>>, response: Response<List<Director>>) {
                if(response.isSuccessful) {
                    directors = response.body()!!
                    updateDirectors()
                } else {
                    createToast("Erro de comunicaçao com o servidor!\nPor favor tente novamente")
                }
            }

            override fun onFailure(call: Call<List<Director>>, t: Throwable) {
                createToast("Erro de comunicaçao com o servidor!\nPor favor tente novamente")
            }
        })
    }
}