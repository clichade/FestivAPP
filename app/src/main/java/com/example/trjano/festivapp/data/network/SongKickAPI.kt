package com.example.trjano.festivapp.data.network

import android.util.Log
import com.example.trjano.festivapp.data.database.EventItem

import org.json.JSONArray
import com.example.trjano.festivapp.utilities.Util
import org.json.JSONObject
import java.net.URL

/**
 * Implementation of Songkick API as Singleton
 */
object SongKickAPI {

    /**Songkick KEY is needed to access the Songkick API**/
    private const val KEY: String = "BsmQKU834Qlfu4Ap"


    /**
     * Searches events in the SongKick API and returns them as an ArrayList of EventItem
     * for the non obligatory the default value is "none"
     *
     * @param location_name* Name of the Province we want to search OBLIGATORY
     * @param event_name fragment of the total name of the event we want to search OPTIONAL
     * @param date_start minimum date to start searching the events OPTIONAL
     * @param date_end maximum date to start searching the events OPTIONAL
     * @param type* type of event it can be CONCERT, FESTIVAL or both, default as both CONCERT|FESTIVAL
     *
     * @return the list of event as ArrayList<EventItem>
     */
    fun find(location_name: String, event_name: String = Util.NONE,
             date_start: String = Util.NONE , date_end : String = Util.NONE,
             type: String = Util.TYPE_BOTH): ArrayList<EventItem>{


        val event_list = ArrayList<EventItem>()
        val location_id = getCityID(location_name)//obtains the ID of the given location


        var result: String
        //if the date has ben setted the URL will contain them as parameters
        if(date_start != Util.NONE && date_end != Util.NONE)
             result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY&min_date=$date_start&max_date=$date_end").readText()
        else//the date has not been setted
             result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY").readText()

        //access the JSONArray event list
        var json = JSONObject(result)
        val events = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event")

        //Iterate over each Json Event within the list
        for (i in 0..(events.length() -1)) {
            var event_item: EventItem = genEventFromJson(events.getJSONObject(i))

            if(event_name != Util.NONE) {//If name has ben setted
                if (event_item.name!!.contains(event_name, true)) event_list.add(event_item)
            }//If name has not been setted
            else event_list.add(event_item)

        }
        return event_list

    }

    /**
     * Dada una localición devuelve los futuros eventos en forma de lista de EventItem,
     * llamando a la api de Songkick
     * @param location_name
     */
    fun find(location_name: String): ArrayList<EventItem>{

        val event_list = ArrayList<EventItem>()
        val location_id = getCityID(location_name)

        val result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY").readText()

        var json = JSONObject(result)
        val events = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event")

        for (i in 0..(events.length() -1)) {
            var event_item: EventItem = genEventFromJson(events.getJSONObject(i))
            event_list.add(event_item)

        }

        return event_list
    }

    /**
     * Returns an array with events given a name of the event and location
     * @param location_name
     * @param event_name
     */
    fun find(location_name: String, event_name: String): ArrayList<EventItem>{
        val event_list = ArrayList<EventItem>()
        val location_id = getCityID(location_name)

        val result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY").readText()

        var json = JSONObject(result)
        val events = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event")

        for (i in 0..(events.length() -1)) {
            var event_item: EventItem = genEventFromJson(events.getJSONObject(i))
            if (event_item.name!!.contains(event_name, true)) {
                event_list.add(event_item)
            }

        }
        return event_list
    }


    /**
     * Given an Event in JSON format from the Songkick API returns it as an EventItem instance
     *
     */
    private fun genEventFromJson(event_json: JSONObject): EventItem {
        val id = event_json.getInt("id")

        val name = event_json.getString("displayName").substringBefore(" at ")
        val city = event_json.getJSONObject("location").getString("city")
        val start_date = event_json.getJSONObject("start").getString("date")
        val location = event_json.getJSONObject("venue").getString("displayName")
        val artists = getArtistFromJson(event_json.getJSONArray("performance"))

        val type_real = event_json.getString("type")
        var type = "0"//Check if it is a festival or a concert
        if (type_real.equals("Festival")) {type = "1"}

        val event = EventItem(0,id.toLong(), name, city, start_date, location, artists, type)
        return event
    }

    /**
     * Given the Songkick ID of an event returns the URI from the SongKick page of given event
     *
     */
    fun getSongKickUri(id: Long): String{

        val result = URL("https://api.songkick.com/api/3.0/events/$id.json?apikey=$KEY").readText()
        var json = JSONObject(result)
        val uri =json.getJSONObject("resultsPage").getJSONObject("results").getJSONObject("event").getString("uri")
        return uri
    }

    /**
     * Given the Songkick ID of an event returns a Pair containing the Latitude and Longitude of the event location
     * @return Pair(Latitude: Long, Longitude: Long)
     */
    fun getEventCoordinates(id: Long): Pair<Long,Long>{

        val result = URL("https://api.songkick.com/api/3.0/events/$id.json?apikey=$KEY").readText()
        var json = JSONObject(result)
        val json_location =json.getJSONObject("resultsPage").getJSONObject("results").getJSONObject("event").getJSONObject("location")
        val coordinates = Pair(json_location.getLong("lat"),json_location.getLong("lng"))
        return coordinates

    }

    /**
     * Returns an array with the artists of the event by parsing the JSON Array
     * @param performances
     */
    private fun getArtistFromJson(performances: JSONArray): String {
        var artists = ""
        for (i in 0..(performances.length() -1)){
            artists += "${performances.getJSONObject(i).getString("displayName")}, "
        }

        artists = artists.removeSuffix(", ")

        return artists
    }


    /**
     * Given the name of a Spanish province returns the ID of the given province
     * @param city_name name of a Spanish province
     */
    fun getCityID(city_name : String): String {
        var id = -1

        var result = URL("https://api.songkick.com/api/3.0/search/locations.json?query=$city_name&apikey=$KEY").readText()
        var json = JSONObject(result)

        val locations = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("location")

        //Comprueba que la localización deseada  se encuentra en España
        for (i in 0..(locations.length() - 1)) {
            val location = locations.getJSONObject(i)
            val country_name = location.getJSONObject("metroArea").getJSONObject("country").getString("displayName")
            if (country_name == "Spain"){
                id = location.getJSONObject("metroArea").getInt("id")
                break
            }

        }
        return id.toString()
    }
}
