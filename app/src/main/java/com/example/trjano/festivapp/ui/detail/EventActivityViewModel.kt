package com.example.trjano.festivapp.ui.detail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.EventLog

import com.example.trjano.festivapp.data.EventRepository
import com.example.trjano.festivapp.data.database.EventItem

class EventActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: EventRepository = EventRepository.getInstance(this.getApplication<Application>().applicationContext)

    val eventItem = MutableLiveData<EventItem>()

    fun setValue(event: EventItem){
        eventItem.value = event
    }

    /**
     * Todo implementar viewModel
    ViewModel: Contendrá las llamadas al repositorio de AñadirFAvoritos EliminarFAvoritos (y lo mismo para Pendiente y Asistidos) teniendo en cuenta que la lógica se hace
    desde respository. Además en esos métodos se modificará el valor de la instancia event de viewmodel.
    Ej:
    fun añadirFavoritos() {
    event.favorite = 1
    mRepository.AñadirFavoritos(event)
    }

    Por último ViewModel contendrá 3 métodos que devuelvan booleano para comprobar si esFavorito, esPendiente, esAsistido

     */


}
