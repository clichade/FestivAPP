package com.example.trjano.festivapp.data.network

import android.util.Log
import com.example.trjano.festivapp.data.database.EventItem
import org.json.JSONArray

import org.json.JSONObject
import java.net.URL

/**
 * Implementation of Songkick API as Singleton
 */
object SongKickAPI {

    /**Songkick KEY*/
    private const val KEY: String = "BsmQKU834Qlfu4Ap"

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
     * Dado un objeto de evento JSON de la API Songkick crea una instancia
     * EventItem a partir de ese objeto.
     * @param event_json
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
     * Dado el nombre de una ciudad de España, realiza una llamada a la API de SongKick y
     * devuelve su ID.
     * En caso de que no es encuentre el resultado será -1
     * @param city_name
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
