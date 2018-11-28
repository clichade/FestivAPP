package com.example.trjano.festivapp.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.trjano.festivapp.data.EventRepository;
import com.example.trjano.festivapp.data.database.EventItem;

public class EventActivityViewModel extends ViewModel {

    private final EventRepository mRepository;
    private MutableLiveData<EventItem> eventItem;

    public EventActivityViewModel(EventRepository repository) {
        this.mRepository = repository;
        //TODO: Hay que cargar algun evento?
        eventItem = null;
    }

    public MutableLiveData<EventItem> getEventItem(){
        return eventItem;
    }
    public void setEventItem(EventItem eventItem){
        this.eventItem.postValue(eventItem);
    }
}
