package com.dietrich.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private var token: Int = -1
    private lateinit var tokenField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tokenField = findViewById(R.id.token)

        token = State["token"] as Int
        tokenField.text = "$token"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.movies -> {
                goToPage(MoviesActivity::class.java)
            }
            R.id.directors -> {
                goToPage(DirectorsActivity::class.java)
            }
            R.id.confirm -> {
                goToPage(ConfirmActivity::class.java)
            }
            R.id.exit -> {
                createToast("Exit")
                finishAffinity()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}