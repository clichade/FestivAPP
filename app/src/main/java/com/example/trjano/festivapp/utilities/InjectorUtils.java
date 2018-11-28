package com.example.trjano.festivapp.utilities;

import android.content.Context;
import android.util.EventLog;

import com.example.trjano.festivapp.AppExecutors;
import com.example.trjano.festivapp.data.EventRepository;
import com.example.trjano.festivapp.data.database.AppDatabase;
import com.example.trjano.festivapp.data.network.SongKickAPI;
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
        AppDatabase db = AppDatabase.getDatabase(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        SongKickAPI api = SongKickAPI.INSTANCE;
        return EventRepository.getInstance(db.eventDAO(),api,executors);
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
