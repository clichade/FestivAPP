package com.example.trjano.festivapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import java.net.URL
import kotlin.concurrent.thread

class FinderMainActivity : AppCompatActivity() {
    enum class Focus {NONE, NAME, LOCATION, DATE_FROM, DATE_TO}

    val KEY: String = "BsmQKU834Qlfu4Ap"
    var FOCUS : Focus = Focus.NONE

    private val btn_search : Button by bind(R.id.finder_btn_search)//Delegate
    private val et_name : EditText by bind(R.id.finder_et_name);
    private val et_location : AutoCompleteTextView by bind(R.id.finder_et_location)
    private val et_date_from : EditText by bind(R.id.finder_et_date_from)
    private val et_date_to : EditText by bind(R.id.finder_et_date_to)
    private val menu_list : RecyclerView by bind(R.id.finder_menu_list)

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finder_activity_main)
        components_setup()



       thread () {

            val result = URL("https://api.songkick.com/api/3.0/search/locations.json?query=Cáceres&apikey=${KEY}").readText()
            Log.d("json", result)

            val venue = URL("https://api.songkick.com/api/3.0/metro_areas/28712/calendar.json?apikey=${KEY}").readText()
            Log.d("venue", venue)
        }
    }

    fun components_setup() {
        FOCUS = Focus.NONE

        location_setup()
        menu_list_setup()


    }

    /*
    Sets the autocomplete textview with the list of Locations lodades from the strings xml
    also sets the minimum number of characters needed for start the autocompletion
     */
    fun location_setup(){
        val locations_array : Array<String> = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations_array)
        et_location.setAdapter(adapter)
        et_location.threshold = 2
    }

    fun menu_list_setup() {

        viewManager = LinearLayoutManager(this)
        viewAdapter = FinderMenuListAdapter(resources.getStringArray(R.array.finder_menu_list))


        menu_list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }


    }


    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }
}
