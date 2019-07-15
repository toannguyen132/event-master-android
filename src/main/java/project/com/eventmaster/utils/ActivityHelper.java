package project.com.eventmaster.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.ui.detail.DetailActivity;

public class ActivityHelper {

    static private ActivityHelper instance;

    static public ActivityHelper getInstance() {
        if (instance == null){
            instance = new ActivityHelper();
        }
        return instance;
    }

    public void openEventDetail(Context context, Event event) {
        Intent intent = new Intent(context, DetailActivity.class);

        Gson gson = new Gson();
        String eventData = gson.toJson(event);
        intent.putExtra("event", eventData);
        context.startActivity(intent);
    }

    public void openEventDetail(Context context, String eventId) {
        Intent intent = new Intent(context, DetailActivity.class);

        intent.putExtra("eventId", eventId);
        context.startActivity(intent);
    }
}
