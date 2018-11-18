package com.example.trjano.festivapp

import com.example.trjano.festivapp.eventhierarchy.EventItem
import org.json.JSONArray

import org.json.JSONObject
import java.net.URL

class SongKickAPI {
    companion object {

        //Nuestra Key de la API de Songkick
        val KEY: String = "BsmQKU834Qlfu4Ap"


        /**
         * Dada una localición devuelve los futuros eventos en forma de lista de EventItem, llamando a la api de Songkick
         */
        fun find(location_name: String): ArrayList<EventItem>{
            val event_list = ArrayList<EventItem>()
            val location_id = getCityID(location_name)

            val result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY").readText()

            var json = JSONObject(result)
            val events = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event")


            // var event1 = EventItem(0,"Evento1","Ciudad1","1/1/2018","2/1/2018","Localizacion1","Artista1","0")
            for (i in 0..(events.length() -1)) {
                var event_item: EventItem = genEventFromJson(events.getJSONObject(i))
                event_list.add(event_item)

            }


            return event_list
        }

        fun find(location_name: String, event_name: String): ArrayList<EventItem>{
            val event_list = ArrayList<EventItem>()
            val location_id = getCityID(location_name)

            val result = URL("https://api.songkick.com/api/3.0/metro_areas/$location_id/calendar.json?apikey=$KEY").readText()

            var json = JSONObject(result)
            val events = json.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event")


            // var event1 = EventItem(0,"Evento1","Ciudad1","1/1/2018","2/1/2018","Localizacion1","Artista1","0")
            for (i in 0..(events.length() -1)) {
                var event_item: EventItem = genEventFromJson(events.getJSONObject(i))
                if (event_item.getMName().contains(event_name,true)) {
                    event_list.add(event_item)
                }

            }


            return event_list

        }


        /**
         * Dado un objeto de evento JSON de la API Songkick crea una instancia EventItem a partir de ese objeto.
         */
        private fun genEventFromJson(event_json: JSONObject): EventItem{
            val id = event_json.getInt("id")

            val name = event_json.getString("displayName").substringBefore(" at ")
            val city = event_json.getJSONObject("location").getString("city")
            val start_date = event_json.getJSONObject("start").getString("date")
            val location = event_json.getJSONObject("venue").getString("displayName")
            val artists = getArtistFromJson(event_json.getJSONArray("performance"))

            val type_real = event_json.getString("type")
            var type = "0"//Check if it is a festival or a concert
            if (type_real.equals("Festival")) {type = "1"}

            val event = EventItem(id.toLong(),name,city,start_date,location,artists,type)
            return event
        }

        private fun getArtistFromJson(performances: JSONArray): String{
            var artists = ""
            for (i in 0..(performances.length() -1)){
                artists += "${performances.getJSONObject(i).getString("displayName")}, "
            }

            artists = artists.removeSuffix(", ")

            return artists
        }


        /**
         * Dado el nombre de una ciudad de España, realiza una llamada a la API de SongKick y devuelve su ID.
         * En caso de que no es encuentre el resultado será -1
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
}