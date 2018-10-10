package com.example.trjano.festivapp

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    enum class Focus {NONE, NAME, LOCATION, DATE_FROM, DATE_TO}

    val KEY: String = "BsmQKU834Qlfu4Ap"
    var FOCUS : Focus = Focus.NONE

    private val btn_search : Button by bind(R.id.finder_btn_search)//Delegate
    private val et_name : EditText by bind(R.id.finder_et_name);
    private val et_location : AutoCompleteTextView by bind(R.id.finder_et_location)
    private val et_date_from : EditText by bind(R.id.finder_et_date_from)
    private val et_date_to : EditText by bind(R.id.finder_et_date_to)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component_setup()



       thread () {

            val result = URL("https://api.songkick.com/api/3.0/search/locations.json?query=Cáceres&apikey=${KEY}").readText()
            Log.d("json", result)

            val venue = URL("https://api.songkick.com/api/3.0/metro_areas/28712/calendar.json?apikey=${KEY}").readText()
            Log.d("venue", venue)
        }
    }

    fun component_setup() {
        FOCUS = Focus.NONE

        val locations_array : Array<String> = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations_array)
        et_location.setAdapter(adapter)
        et_location.threshold = 2
    }


    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }
}
