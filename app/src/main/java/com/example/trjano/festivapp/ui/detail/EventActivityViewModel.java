package com.example.trjano.festivapp.ui.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.trjano.festivapp.data.database.EventItem;

public class EventActivityViewModel extends ViewModel {
    private MutableLiveData<EventItem> eventItem;

    public EventActivityViewModel(MutableLiveData<EventItem> eventItem) {
        eventItem = new MutableLiveData<>();
    }

    public MutableLiveData<EventItem> getEventItem(){
        return eventItem;
    }
    public void setEventItem(EventItem eventItem){
        this.eventItem.postValue(eventItem);
    }
}
