package com.example.trjano.festivapp.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.Serializable
import java.lang.Exception

class EventListActivity : AppCompatActivity() {

    lateinit var binding: ActivityEventListBinding
    private lateinit var viewAdapter: EventListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mViewModel: EventListActivityViewModel
    var eventList : List<EventItem>? = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)


        mViewModel = ViewModelProviders.of(this).get(EventListActivityViewModel(application)::class.java)
        mViewModel.setValue(this.eventList)

        event_list_setup()//prepare the list for working properly

        mViewModel.eventList.observe(this, Observer { event_list ->
            async {
                val type = intent.extras.getString("Type")

                //if there is no Type it is a searc otherwise it belongs to our own concent
                if (type.isNullOrBlank()) load_search()
                else load_table()

                uiThread { viewAdapter.update(ArrayList(eventList)) }
            }
        })
    }

    fun load_table(){

        when (intent.extras.getString("Type")){
            "FAVORITES_EVENTS" -> eventList = mViewModel.getAllFavoriteEvents()
            "UPCOMING_EVENTS" -> eventList = mViewModel.getAllUpcomingEvents()
            "PAST_EVENTS" -> eventList = mViewModel.getAllPastEvents()
        }

        if (eventList == null) eventList = listOf()
    }

    /**
     * Una vez comprobado que se ha pedido unas búsqueda. Se realizará una bússqueda en función de los parámetros insertados
     */
    fun load_search(){

        val location = intent.extras.getString("location")
        val name = intent.extras.getString("name")
        val start_date = intent.extras.getString("start_date")
        val end_date = intent.extras.getString("end_date")
        val type = intent.extras.getString("search_type")

              try {
                    eventList = mViewModel.find(location,name,start_date,end_date,type)
                }
                catch (e: Exception){
                    Log.d("prueba", e.message)
                }





    }

    /**
     * This method sets te behaviour of the list including the 2 column format and the effect when pressing one event
     */
    fun event_list_setup(){

        viewManager = GridLayoutManager(this,2)
        viewAdapter = EventListAdapter(ArrayList(eventList))


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
