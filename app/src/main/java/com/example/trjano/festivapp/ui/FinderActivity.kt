package com.example.trjano.festivapp.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.*
import com.example.trjano.festivapp.ui.list.EventListActivity
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.databinding.FinderActivityBinding
import com.example.trjano.festivapp.utilities.transform_date
import java.text.SimpleDateFormat
import java.util.*

class FinderActivity : AppCompatActivity() {

    lateinit var binding : FinderActivityBinding
    val KEY: String = "BsmQKU834Qlfu4Ap"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.finder_activity)
        components_setup()

    }

    fun components_setup() {

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
        binding.finderEtLocation.setAdapter(adapter)
        binding.finderEtLocation.threshold = 2
    }


    fun btn_setup(){
        binding.finderBtnSearch.setOnClickListener {
            if (!check_input_errors()){



                val intent = Intent(this, EventListActivity::class.java)

                if (binding.finderEtName.text.toString().isNotEmpty())
                    intent.putExtra("name", binding.finderEtName.text.toString())

                //location
                intent.putExtra("location",binding.finderEtLocation.text.toString())
                startActivity(intent)
            }

        }
    }

    fun check_input_errors(): Boolean {
        var isError= false

        //if locations is empty
        if (binding.finderEtLocation.text.toString().isEmpty()){
            binding.finderLabelError.text = resources.getString(R.string.finder_error_no_location)
            return true
        }

        //if locations does not belong to the list of locations
        if (!resources.getStringArray(R.array.locations).contains(binding.finderEtLocation.text.toString())){
            binding.finderLabelError.text = resources.getString(R.string.finder_error_location_invalid)
            return true
        }

        //if date starts exists but date end doesn't the search can not be done
        if (!binding.finderEtDateFrom.text.toString().isEmpty() && binding.finderEtDateTo.text.toString().isEmpty()){
            binding.finderLabelError.text = resources.getString(R.string.finder_error_no_end_date_selected)
            return true
        }


        if (!binding.finderEtDateFrom.text.toString().isEmpty() && !binding.finderEtDateTo.text.toString().isEmpty()) {
            val date1_start = SimpleDateFormat("dd/MM/yyyy").parse(binding.finderEtDateFrom.text.toString())
            val date1_end = SimpleDateFormat("dd/MM/yyyy").parse(binding.finderEtDateTo.text.toString())


            Log.d("fecha", "Inicio: " + date1_start.toString())
            Log.d("fecha", "Final: " + date1_end.toString())

            //if date end is sooner than date start the search can not be done
            if (date1_start > date1_end) {
                binding.finderLabelError.text = resources.getString(R.string.finder_error_dates_invalid)
                return true
            }
        }

        if(!binding.finderCheckboxConcert.isChecked && !binding.finderCheckboxFestival.isChecked){
            binding.finderLabelError.text = resources.getString(R.string.finder_error_no_type_selected)
            return true
        }

        return isError
    }


    fun date_from_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        binding.finderEtDateFrom.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener({_,year_start, month_start, day_start ->
                binding.finderEtDateFrom.setText(transform_date(year_start, month_start, day_start))})
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()

        }

    }

    fun date_to_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        binding.finderEtDateTo.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener({_,year_start, month_start, day_start ->
                binding.finderEtDateTo.setText(transform_date(year_start, month_start, day_start))})
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()

        }
    }
}
