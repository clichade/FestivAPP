package com.example.trjano.festivapp.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.trjano.festivapp.data.database.EventItem;

public class EventActivityViewModel extends AndroidViewModel {

    private final EventRepository mRepository;

    private MutableLiveData<EventItem> eventItem;

    public EventActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = EventRepository.getInstance(this.getApplication().getApplicationContext());
        eventItem = null;
    }


    public MutableLiveData<EventItem> getEventItem(){
        return eventItem;
    }

    public void setEventItem(EventItem eventItem){
        this.eventItem.postValue(eventItem);
    }
}
