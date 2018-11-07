package com.example.trjano.festivapp

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.trjano.festivapp.database.AdminSQLiteOpenHelper

import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {

    private val event_list : RecyclerView by bind(R.id.eventlist_list)

    private lateinit var viewAdapter: EventListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        components_setup()
    }

    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }


    fun components_setup(){
        event_list_setup()
    }

    fun event_list_setup(){
       /* val table: String = intent.extras.getString("Type").toString()
        val admin  =  AdminSQLiteOpenHelper(this, table,null,1)
        val db = admin.writableDatabase
        val tuple = db.rawQuery("SELECT * FROM $table",null)
        val list: MutableList<String> = mutableListOf()
        var i = 0
        while(tuple.moveToNext()){
            list.add(i,tuple.getString(i))
            i++
        }*/

        viewManager = GridLayoutManager(this,2)
        viewAdapter = EventListAdapter(arrayOf("1","2","3","4","5","6","7","8"))

        //tuple.close()

        viewAdapter.onItemClick = { String ->
            Log.d("dev",String)
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }





        event_list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }

    }
}
