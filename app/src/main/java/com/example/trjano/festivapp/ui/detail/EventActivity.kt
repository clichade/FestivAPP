package com.example.trjano.festivapp.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.annotation.IdRes
import android.view.View
import android.widget.ImageButton
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.AppDatabase.getDatabase
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.databinding.ActivityEventBinding
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast
import android.arch.lifecycle.ViewModelProviders




class EventActivity : AppCompatActivity() {


    lateinit var binding: ActivityEventBinding
    lateinit var mViewModel : EventActivityViewModel

    private val btn_favorite : ImageButton by bind(R.id.event_btn_favorites)
    private val btn_pending : ImageButton by bind(R.id.event_btn_pending)
    private val btn_assisted : ImageButton by bind(R.id.event_btn_assisted)

    private var is_fav = false
    private var is_pending = false
    private var is_assisted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.extras.get("event") as EventItem

        mViewModel = ViewModelProviders.of(this).get(EventActivityViewModel::class.java)

        //TODO:?
//        mViewModel.eventItem.observe(this,  ->{
//
//        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        binding.apply {
            eventName.text = event.name
            eventArtists.text = event.artists
            eventLocationCity.text = event.city
            eventLocationExact.text = event.location
            eventDateFrom.text = event.startDate
        }
        check_status(event)
        btn_setup(event)
    }


    /**
     * Comprueba si pertenece a una tabla o no, debe ir antes de btn_setup()
     */
    fun check_status(event: EventItem){
        val db : AppDatabase = getDatabase(this)

        //TODO: Cuelga aquí, hay que implementar el async posiblmente:
        //TODO: annot access database on the main thread since it may potentially lock the UI for a long period of time.
        if (db.eventDAO().getEvent(event._id).favorite==1){
            btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
            is_fav = true
        }

        if (db.eventDAO().getEvent(event._id).upcoming==1){
            btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
            is_pending = true
        }

        if (db.eventDAO().getEvent(event._id).assisted==1){
            btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
            is_assisted = true
        }

    }


    fun btn_setup(event: EventItem){

        val db : AppDatabase = getDatabase(this)
        btn_favorite.setOnClickListener {

            if (is_fav) {
                db.eventDAO().deleteEvent(event)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite_no)
                is_fav = false
                toast(resources.getString(R.string.toast_removed_favor))
            } else {
                event.favorite=1
                db.eventDAO().insertEvent(event)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
                is_fav = true
                toast(resources.getString(R.string.toast_add_favorites))

            }
        }


        btn_pending.setOnClickListener {
            if(is_pending){
                db.eventDAO().deleteEvent(event)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending_no)
                is_pending = false
                toast(resources.getString(R.string.toast_removed_pending))
            }
            else {
                event.upcoming=1
                db.eventDAO().insertEvent(event)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
                is_pending = true
                toast(resources.getString(R.string.toast_add_pending))

            }

        }

        btn_assisted.setOnClickListener {
            if(is_assisted){
                db.eventDAO().deleteEvent(event)
                btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted_no)
                is_assisted = false
                toast(resources.getString(R.string.toast_removed_assisted))
            }
            else {
                event.assisted=1
                db.eventDAO().insertEvent(event)
                btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
                is_assisted = true
                toast(resources.getString(R.string.toast_add_assisted))

            }

        }
    }
    /**
     * Method that opens main Songkick website
     */
    fun openSongKickLink(view: View){
        //TODO: Modificar con enlace de la pagina del evento de Songkick
        val uri = Uri.parse("https://www.songkick.com/")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        return lazy { findViewById<T>(res) }
    }
}
