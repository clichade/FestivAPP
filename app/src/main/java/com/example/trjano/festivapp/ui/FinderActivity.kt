package com.example.trjano.festivapp.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.trjano.festivapp.ui.list.EventListActivity
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.databinding.FinderActivityBinding
import com.example.trjano.festivapp.utilities.Util

import java.text.SimpleDateFormat
import java.util.*

class FinderActivity : AppCompatActivity() {

    /**For binding View elements to layout*/
    lateinit var binding : FinderActivityBinding

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

    /**
     * Sets the autocomplete textview with the list of Locations loaded from the strings xml
     * also sets the minimum number of characters needed for start the autocompletion
     */
    fun location_setup() {
        val locations_array : Array<String> = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,locations_array)
        binding.finderEtLocation.setAdapter(adapter)
        binding.finderEtLocation.threshold = 2
    }


    fun btn_setup(){
        binding.finderBtnSearch.setOnClickListener {
            if (!input_error()){//if ther are not input errors

                val intent = Intent(this, EventListActivity::class.java)

                //adding the name as extra
                var name_extra = Util.NONE
                if (binding.finderEtName.text.toString().isNotEmpty())
                    name_extra = binding.finderEtName.text.toString()

                intent.putExtra("name", name_extra)


                //adding the type as extra
                var type_extra = Util.TYPE_BOTH
                if (binding.finderCheckboxConcert.isChecked && binding.finderCheckboxFestival.isChecked)
                    type_extra = Util.TYPE_BOTH
                else if (binding.finderCheckboxConcert.isChecked)
                    type_extra = Util.TYPE_CONCERT
                else
                    type_extra = Util.TYPE_FESTIVAL

                intent.putExtra("search_type", type_extra)

                //da date as extra
                var start_date = Util.NONE
                var end_date = Util.NONE
                if (binding.finderEtDateFrom.text.toString().isNotEmpty()){
                    start_date = binding.finderEtDateFrom.text.toString()
                    end_date = binding.finderEtDateTo.text.toString()
                }

                intent.putExtra("start_date", start_date)
                intent.putExtra("end_date", end_date)



                //add location as extra
                intent.putExtra("location",binding.finderEtLocation.text.toString())
                startActivity(intent)
            }

        }
    }

    /**
     * Checks for any input error that could make the search fail.
     * @return true if there is an least 1 error, false if no errors where found.
     */
    fun input_error(): Boolean {
        var isError= false //default no error

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

        //if date end is sooner than date start the search can not be done
        if (!binding.finderEtDateFrom.text.toString().isEmpty() && !binding.finderEtDateTo.text.toString().isEmpty()) {
            val date1_start = SimpleDateFormat("yyyy-MM-dd").parse(binding.finderEtDateFrom.text.toString())
            val date1_end = SimpleDateFormat("yyyy-MM-dd").parse(binding.finderEtDateTo.text.toString())

            //if date end is sooner than date start the search can not be done
            if (date1_start > date1_end) {
                binding.finderLabelError.text = resources.getString(R.string.finder_error_dates_invalid)
                return true
            }
        }

        //If no type Concert, Festival or both has been checked
        if(!binding.finderCheckboxConcert.isChecked && !binding.finderCheckboxFestival.isChecked){
            binding.finderLabelError.text = resources.getString(R.string.finder_error_no_type_selected)
            return true
        }
        return isError
    }


    /**
     * Set ups the finderEtDateFrom with a date picker and the necessary date format
     */
    fun date_from_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        binding.finderEtDateFrom.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener({_,year_start, month_start, day_start ->
                binding.finderEtDateFrom.setText(Util.format_date(year_start, month_start, day_start))})
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()

        }

    }

    /**
     * Set ups the finderEtDateTo with a date picker and the necessary date format
     */
    fun date_to_setup(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)

        binding.finderEtDateTo.setOnClickListener() {
            val from_listener = DatePickerDialog.OnDateSetListener { _, year_start, month_start, day_start ->
                binding.finderEtDateTo.setText(Util.format_date(year_start, month_start, day_start))
            }
            val dpd = DatePickerDialog( this,from_listener,year,month,day)
            dpd.show()
        }
    }
}
