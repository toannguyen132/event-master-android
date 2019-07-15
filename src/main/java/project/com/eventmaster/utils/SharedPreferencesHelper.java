package project.com.eventmaster.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferencesHelper {

    static SharedPreferencesHelper instance;

    public static SharedPreferencesHelper getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesHelper();
        }
        return instance;
    }

    private static String PREF = "eventPref";

    public final static String KEY_CURRENT_USER = "currentUser";

    private Gson gson;

    public SharedPreferencesHelper() {
        this.gson = new Gson();
    }

    public <T> T getObject(String key, Class<T> type) {
        Context context = GlobalContext.getInstance().getContext();
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        String json = pref.getString(key, null);

        return gson.fromJson(json, type);
    }

    public <T> void setObject(T object, String key) {
        Context context = GlobalContext.getInstance().getContext();
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.commit();
    }
}
