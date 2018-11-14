package com.example.trjano.festivapp

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.*
import java.net.URL
import java.text.SimpleDateFormat
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
    private val tv_error: TextView by bind(R.id.finder_label_error)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finder_activity)
        components_setup()



      /* thread () {
           SongKickAPI.find("Cordoba")
        }*/
    }

    fun components_setup() {
        FOCUS = Focus.NONE

        location_setup()
        date_from_setup()
        date_to_setup()
        btn_setup()
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


    fun btn_setup(){
        btn_search.setOnClickListener {
            if (!check_input_errors()){

                val intent = Intent(this, EventActivity::class.java)

                //location
                intent.putExtra("location",et_location.text.toString())
                startActivity(intent)
            }

        }
    }

    fun check_input_errors(): Boolean {
        var isError= false

        //if locations is empty
        if (et_location.text.toString().isEmpty()){
            tv_error.text = resources.getString(R.string.finder_error_no_location)
            return true
        }

        //if locations does not belong to the list of locations
        if (!resources.getStringArray(R.array.locations).contains(et_location.text.toString())){
            tv_error.text = resources.getString(R.string.finder_error_location_invalid)
            return true
        }

        //if date starts exists but date end doesn't the search can not be done
        if (!et_date_from.text.toString().isEmpty() && et_date_to.text.toString().isEmpty()){
            tv_error.text = resources.getString(R.string.finder_error_no_end_date_selected)
            return true
        }

        val date1_start = SimpleDateFormat("dd/MM/yyyy").parse(et_date_from.text.toString())
        val date1_end = SimpleDateFormat("dd/MM/yyyy").parse(et_date_to.text.toString())


        Log.d("fecha","Inicio: " + date1_start.toString())
        Log.d("fecha","Final: " + date1_end.toString())

        //if date end is sooner than date start the search can not be done
        if (date1_start > date1_end){
            tv_error.text = resources.getString(R.string.finder_error_dates_invalid)
            return true
        }

        if(!cb_concert.isChecked && !cb_festival.isChecked){
            tv_error.text = resources.getString(R.string.finder_error_no_type_selected)
            return true
        }




        return isError
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
