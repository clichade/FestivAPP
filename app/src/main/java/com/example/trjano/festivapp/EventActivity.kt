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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.trjano.festivapp.database.EventItem
import com.example.trjano.festivapp.database.FavoritesCRUD
import com.example.trjano.festivapp.database.PastCRUD
import com.example.trjano.festivapp.database.UpcomingCRUD
import kotlinx.android.synthetic.main.event_list_item.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast


class EventActivity : AppCompatActivity() {

    private val tv_name : TextView by bind(R.id.event_name)
    private val tv_artists : TextView by bind(R.id.event_artists)
    private val tv_location_city : TextView by bind(R.id.event_location_city)
    private val tv_location_exact : TextView by bind(R.id.event_location_exact)
    private val tv_date_from : TextView by bind(R.id.event_date_from)
    private val tv_start_time : TextView by bind(R.id.event_date_start_time)
    private val image: ImageView by bind(R.id.event_imageview)

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

        if (eventItem.getmType() == "0") image.setBackgroundResource(R.drawable.image_concert)
        else image.setBackgroundResource(R.drawable.image_festival)

        tv_name.text = eventItem.getmName()
        tv_artists.text = eventItem.getmArtists()
        tv_location_city.text = eventItem.getmCity()
        tv_location_exact.text = eventItem.getmLocation()
        tv_date_from.text = eventItem.getmStartDate()

        check_status()
        btn_setup()


    }

    /**
     * Comprueba si pertenece a una tabla o no, debe ir antes de btn_setup()
     *
     */
    fun check_status(){
        val eventItem = intent.extras.get("event") as EventItem
        val favoritesCRUD = FavoritesCRUD.getInstance(this)
        val upcomingCRUD = UpcomingCRUD.getInstance(this)
        val pastCRUD = PastCRUD.getInstance(this)

        if (favoritesCRUD.exists(eventItem.getmId())){
            btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
            is_fav = true
        }

        if (upcomingCRUD.exists(eventItem.getmId())){
            btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
            is_pending = true
        }

        if (pastCRUD.exists(eventItem.getmId())){
            btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
            is_assisted = true
        }

    }


    fun btn_setup(){

        val eventItem = intent.extras.get("event") as EventItem
        val favoritesCRUD = FavoritesCRUD.getInstance(this)
        val upcomingCRUD = UpcomingCRUD.getInstance(this)
        val pastCRUD = PastCRUD.getInstance(this)

        btn_favorite.setOnClickListener {

            if (is_fav) {
                favoritesCRUD.deleteFavorite(eventItem)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite_no)
                is_fav = false
                toast(resources.getString(R.string.toast_removed_favor))
            } else {
                favoritesCRUD.insertFavorite(eventItem)
                btn_favorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
                is_fav = true
                toast(resources.getString(R.string.toast_add_favorites))

            }
        }


        btn_pending.setOnClickListener {
            if(is_pending){
                upcomingCRUD.deleteUpcomingEvent(eventItem)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending_no)
                is_pending = false
                toast(resources.getString(R.string.toast_removed_pending))
            }
            else {
                upcomingCRUD.insertUpcomingEvent(eventItem)
                btn_pending.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
                is_pending = true
                toast(resources.getString(R.string.toast_add_pending))

            }

        }

        btn_assisted.setOnClickListener {
            if(is_assisted){
                pastCRUD.deletePastEvent(eventItem)
                btn_assisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted_no)
                is_assisted = false
                toast(resources.getString(R.string.toast_removed_assisted))
            }
            else {
                pastCRUD.insertPastEvent(eventItem)
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
