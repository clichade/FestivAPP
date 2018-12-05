package com.example.trjano.festivapp.ui.list

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.trjano.festivapp.ui.detail.EventActivity
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.data.network.SongKickAPI
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.databinding.ActivityEventListBinding


import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.io.Serializable

class EventListActivity : AppCompatActivity() {

    lateinit var binding: ActivityEventListBinding
    private lateinit var viewAdapter: EventListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)

        event_list_setup()


        if (intent.extras.getString("Type").isNullOrBlank())
            load_search()
        else
            load_table()

    }

    fun load_table(){
        val db : AppDatabase = AppDatabase.getDatabase(this)

        var new_list: List<EventItem> = ArrayList()

        //Todo: Sustituir usando viewmodel
        /*
        when (intent.extras.getString("Type")){
            "FAVORITES_EVENTS" -> new_list = db.eventDAO().getAllFavorites()
            "UPCOMING_EVENTS" -> new_list = db.eventDAO().getAllUpcomingEvents()
            "PAST_EVENTS" -> new_list = db.eventDAO().getAllPastEvents()
            else -> new_list = ArrayList()
        }
        */

        async {
            uiThread { viewAdapter.update(ArrayList(new_list)) }
        }



    }

    fun load_search(){

        val location = intent.extras.getString("location")
        val name = intent.extras.getString("name")

        //Todo: Sustituir usando viewmodel
      /*  async {
            var new_list: ArrayList<EventItem>

            when {
            !name.isNullOrBlank() -> new_list = SongKickAPI.find(location, name)
            else  -> new_list = SongKickAPI.find(location)
            }


            uiThread { viewAdapter.update(new_list) }
        }*/

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


        binding.eventlistList.apply {
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
