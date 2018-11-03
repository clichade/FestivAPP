package com.example.trjano.festivapp

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.*
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class FinderActivity : AppCompatActivity() {
    enum class Focus {NONE, NAME, LOCATION, DATE_FROM, DATE_TO}

    val KEY: String = "BsmQKU834Qlfu4Ap"
    var FOCUS : Focus = Focus.NONE

    private val btn_search : Button by bind(R.id.finder_btn_search)//Delegate
    private val et_name : EditText by bind(R.id.finder_et_name);
    private val et_location : AutoCompleteTextView by bind(R.id.finder_et_location)
    private val et_date_from : EditText by bind(R.id.finder_et_date_from)
    private val et_date_to : EditText by bind(R.id.finder_et_date_to)
    private val cb_festival: CheckBox by bind(R.id.finder_checkbox_festival)
    private val cb_concert: CheckBox by bind(R.id.finder_checkbox_concert)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finder_activity)
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
        date_from_setup()
        date_to_setup()
    }

    /*
    Sets the autocomplete textview with the list of Locations lodades from the strings xml
    also sets the minimum number of characters needed for start the autocompletion
     */
    fun location_setup() {
        val locations_array : Array<String> = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations_array)
        et_location.setAdapter(adapter)
        et_location.threshold = 2
    }


    fun date_from_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        et_date_from.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener({_,year_start, month_start, day_start ->
                et_date_from.setText(transform_date(year_start,month_start,day_start))})
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()

        }

    }

    fun date_to_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        et_date_to.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener({_,year_start, month_start, day_start ->
                et_date_to.setText(transform_date(year_start,month_start,day_start))})
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()

        }
    }




    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }
}
