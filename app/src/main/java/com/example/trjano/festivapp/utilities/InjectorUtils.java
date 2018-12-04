package com.example.trjano.festivapp.utilities;

import android.content.Context;

import com.example.trjano.festivapp.ui.detail.EventActivityViewModel;

/**
 * Provides static methods to inject the various classes needed for the app
 * @version 1
 */
public class InjectorUtils {

    /**
     * Gets the repository as Singleton
     * @param context
     * @return
     */
    private static EventRepository provideRepository(Context context){
        return EventRepository.getInstance(context);
    }

    /**
     * Provides the EventActivity View Model
     * @param context
     * @return
     */
    public static EventActivityViewModel provideEventActivityViewModel(Context context){
        EventRepository repository = provideRepository(context.getApplicationContext());
        return new EventActivityViewModel(repository);
    }
}
