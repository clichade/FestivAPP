package com.example.trjano.festivapp.ui

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.trjano.festivapp.ui.list.EventListActivity
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**For binding View elements to layout*/
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        components_setup()
    }


    private fun components_setup(){
        binding.mainBtnSearch.setOnClickListener {
            val intent = Intent(this, FinderActivity::class.java)
            startActivity(intent)
        }

        binding.mainBtnFavorites.setOnClickListener {
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","FAVORITES_EVENTS")
            startActivity(intent)
        }

        binding.mainBtnPending.setOnClickListener() {
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","UPCOMING_EVENTS")
            startActivity(intent)
        }

        binding.mainBtnAssisted.setOnClickListener() {
            val intent = Intent(this, EventListActivity::class.java)
            intent.putExtra("Type","PAST_EVENTS")
            startActivity(intent)
        }
    }

}
