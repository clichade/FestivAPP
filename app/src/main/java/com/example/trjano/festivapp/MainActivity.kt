package com.example.trjano.festivapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val KEY: String = "BsmQKU834Qlfu4Ap"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread () {

            val result = URL("https://api.songkick.com/api/3.0/search/artists.json?apikey=${KEY}&query=desakato").readText()
            Log.d("json", result)
        }
    }
}
