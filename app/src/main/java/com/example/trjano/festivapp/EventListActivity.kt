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
import com.example.trjano.festivapp.database.EventItem
import com.example.trjano.festivapp.database.UpcomingCRUD

import kotlinx.android.synthetic.main.activity_event_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.io.Serializable

class EventListActivity : AppCompatActivity() {

    private val event_list : RecyclerView by bind(R.id.eventlist_list)
  //  private val mItems = arrayListOf<EventItem>()
    private lateinit var viewAdapter: EventListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        components_setup()


        if (intent.extras.getString("Type").isNullOrBlank()) load_search()
        else load_table()

    }

    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }


    fun components_setup(){
        event_list_setup()
    }


    fun load_table(){

    }

    fun load_search(){

        val location = intent.extras.getString("location")
        async {
            val new_list = SongKickAPI.find(location)
            uiThread { viewAdapter.update(new_list) }
        }

    }

    fun event_list_setup(){

        val list = arrayListOf<EventItem>()

        viewManager = GridLayoutManager(this,2)
        viewAdapter = EventListAdapter(list)


        viewAdapter.onItemClick = { EventItem ->
            Log.d("dev",EventItem.toString())

            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event",EventItem as Serializable)
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
