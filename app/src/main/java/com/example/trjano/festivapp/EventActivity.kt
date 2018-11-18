package com.example.trjano.festivapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_event.*
import android.content.Intent
import android.net.Uri
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.trjano.festivapp.database.AppDatabase
import com.example.trjano.festivapp.database.AppDatabase.getDatabase
import com.example.trjano.festivapp.eventhierarchy.EventItem
import com.example.trjano.festivapp.eventhierarchy.FavoriteItem
import com.example.trjano.festivapp.eventhierarchy.PastItem
import com.example.trjano.festivapp.eventhierarchy.UpcomingItem
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast


class EventActivity : AppCompatActivity() {

    private val tv_name : TextView by bind(R.id.event_name)
    private val tv_artists : TextView by bind(R.id.event_artists)
    private val tv_location_city : TextView by bind(R.id.event_location_city)
    private val tv_location_exact : TextView by bind(R.id.event_location_exact)
    private val tv_date_from : TextView by bind(R.id.event_date_from)
    private val tv_start_time : TextView by bind(R.id.event_date_start_time)

    private val btn_favorite : ImageButton by bind(R.id.event_btn_favorites)
    private val btn_pending : ImageButton by bind(R.id.event_btn_pending)
    private val btn_assisted : ImageButton by bind(R.id.event_btn_assisted)

    private var is_fav = false
    private var is_pending = false
    private var is_assisted = false





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        setSupportActionBar(toolbar)

        components_setup()
    }


    fun components_setup(){

        val eventItem = intent.extras.get("event") as EventItem
        tv_name.text = eventItem.mName
        tv_artists.text = eventItem.mArtists
        tv_location_city.text = eventItem.mCity
        tv_location_exact.text = eventItem.mLocation
        tv_date_from.text = eventItem.mStartDate

        check_status()
        btn_setup()


    }

    /**
     * Comprueba si pertenece a una tabla o no, debe ir antes de btn_setup()
     *
     */
    fun check_status(){
        val eventItem = intent.extras.get("event") as EventItem
        val db : AppDatabase = getDatabase(this)

        //TODO: Cuelga aquí, hay que implementar el async posiblmente:
        //TODO: annot access database on the main thread since it may potentially lock the UI for a long period of time.
        if (db.eventDAO().getFavorite(eventItem._id)!=null){
            btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
            is_fav = true
        }

        if (db.eventDAO().getUpcomingEvent(eventItem._id)!=null){
            btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
            is_pending = true
        }

        if (db.eventDAO().getPastEvent(eventItem._id)!=null){
            btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
            is_assisted = true
        }

    }



    fun btn_setup(){

        val favoriteItem = intent.extras.get("event") as FavoriteItem
        val pastItem = intent.extras.get("event") as PastItem
        val upcomingItem = intent.extras.get("event") as UpcomingItem

        val db : AppDatabase = getDatabase(this)


        btn_favorite.setOnClickListener {

            if (is_fav) {
                db.eventDAO().deleteFavorite(favoriteItem)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite_no)
                is_fav = false
                toast(resources.getString(R.string.toast_removed_favor))
            } else {
                db.eventDAO().insertFavorite(favoriteItem)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
                is_fav = true
                toast(resources.getString(R.string.toast_add_favorites))

            }
        }


        btn_pending.setOnClickListener {
            if(is_pending){
                db.eventDAO().deleteUpcomingEvent(upcomingItem)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending_no)
                is_pending = false
                toast(resources.getString(R.string.toast_removed_pending))
            }
            else {
                db.eventDAO().insertUpcomingEvent(upcomingItem)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
                is_pending = true
                toast(resources.getString(R.string.toast_add_pending))

            }

        }

        btn_assisted.setOnClickListener {
            if(is_assisted){
                db.eventDAO().deletePastEvent(pastItem)
                btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted_no)
                is_assisted = false
                toast(resources.getString(R.string.toast_removed_assisted))
            }
            else {
                db.eventDAO().insertPastEvent(pastItem)
                btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
                is_assisted = true
                toast(resources.getString(R.string.toast_add_assisted))

            }

        }


    }
    /**
     * Method that opens main Songkick website
     */
    fun OpenSongKickLink(view: View){
        //TODO: Modificar con enlace de la pagina del evento de Songkick
        val uri = Uri.parse("https://www.songkick.com/")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy { findViewById(res) as T }
    }
}
