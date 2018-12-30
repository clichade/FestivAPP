package com.example.trjano.festivapp.ui.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.annotation.IdRes
import android.view.View
import android.widget.ImageButton
import com.example.trjano.festivapp.R
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.databinding.ActivityEventBinding
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast
import android.arch.lifecycle.ViewModelProviders
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

/**
 * Activity with details of an event (concert or festival)
 */
class EventActivity : AppCompatActivity() {

    /**For binding View elements to layout*/
    private lateinit var binding: ActivityEventBinding

    /**Contains the data logic operations*/
    private lateinit var mViewModel: EventActivityViewModel

    /**Bind manually AppBar icons (because ActivityEventBinding don't support them )*/
    private val btnFavorite: ImageButton by bind(R.id.event_btn_favorites)
    private val btnUpcoming: ImageButton by bind(R.id.event_btn_pending)
    private val btnAssisted: ImageButton by bind(R.id.event_btn_assisted)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the event from the previous Activity
        val event = intent.extras.get("event") as EventItem

        //Get the ViewModel from the Activity
        mViewModel = ViewModelProviders.of(this).get(EventActivityViewModel(application)::class.java)


         //Sets the data to the ViewModel
        mViewModel.setValue(event)

        //We set the value to the proper one from database if exists
        async {
            mViewModel.eventSetUp()
        }


        //Bind all the View elements to the layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        binding.apply {
            eventName.text = event.name
            eventArtists.text = event.artists
            eventLocationCity.text = event.city
            eventLocationExact.text = event.location
            eventDateFrom.text = event.startDate
            if (event.type == "0") eventImageview.setBackgroundResource(R.drawable.image_concert_5)
            else eventImageview.setBackgroundResource(R.drawable.image_festival_1)

        }


        //Observing to changing Fav, Pend, Assisted buttons
        mViewModel.liveDataEventItem.observe(this, Observer { event_item ->
            check_status()
            btn_setup()
        })



    }


    /**
     * Loads first-time UI buttons of favorite,upcoming and assisted
     * if should be turned on or not
     */
    private fun check_status() {
        
            if (mViewModel.isFavorite()) {
                btnFavorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
            }
            if (mViewModel.isUpcoming()) {
                btnUpcoming.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
            }
            if (mViewModel.isAssisted()){
                btnAssisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
            }
    }

    /**
     * Load UI buttons for favorite,upcoming and assisted when data changes.
     * Then calls to ViewModel and shows a Toast
     */
    private fun btn_setup() {

            //If clicked, loads icon (turned on/off) asking ViewModel
            btnFavorite.setOnClickListener {

                if (mViewModel.isFavorite()) {
                        btnFavorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite_no)
                        toast(resources.getString(R.string.toast_removed_favor))

                } else {
                        btnFavorite.backgroundDrawable = resources.getDrawable(R.mipmap.ic_favorite)
                        toast(resources.getString(R.string.toast_add_favorites))

                }
                async { mViewModel.updateFavorite()}
            }

            //If clicked, loads icon (turned on/off) asking ViewModel
            btnUpcoming.setOnClickListener {

                    if (mViewModel.isUpcoming()) {
                            btnUpcoming.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending_no)
                            toast(resources.getString(R.string.toast_removed_pending))
                        }

                    else {
                            btnUpcoming.backgroundDrawable = resources.getDrawable(R.mipmap.ic_pending)
                            toast(resources.getString(R.string.toast_add_pending))
                        }

                    //Make the data change
                       async { mViewModel.updateUpcoming()}
            }

            //If clicked, loads icon (turned on/off) asking ViewModel
            btnAssisted.setOnClickListener {

                    if (mViewModel.isAssisted()) {
                            btnAssisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted_no)
                            toast(resources.getString(R.string.toast_removed_assisted))

                    } else {
                            btnAssisted.backgroundDrawable = resources.getDrawable(R.mipmap.ic_assisted)
                            toast(resources.getString(R.string.toast_add_assisted))
                    }
                    //Make the data change

                   async {  mViewModel.updateAssisted()}


            }


    }

    /**
     * Method that opens main Songkick website
     * @param view
     */
    fun openSongKickLink(view: View) {
        async {
            val uri = Uri.parse(mViewModel.getSongkickUri())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            uiThread { startActivity(intent)}
        }
    }

    fun openGoogleMapsLocation(view: View) {

        async {
            val gmmIntentUri = Uri.parse(mViewModel.getGoogleMapsUri())
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            uiThread {
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }

    }

    /**
     * Bind icons from Appbar (fav, upcoming and assisted)
     * @param res
     */
    private fun <T : View> AppCompatActivity.bind(@IdRes res: Int): Lazy<T> {
        return lazy { findViewById<T>(res) }
    }
}
