package project.com.eventmaster.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenHelper {

    static String PREF = "eventPref";
    static String TOKEN_KEY = "token";

    static TokenHelper instance;

    static public TokenHelper getInstance() {
        if (instance == null) {
            instance = new TokenHelper();
        }
        return instance;
    }

    public String getToken() {
        Context context = GlobalContext.getInstance().getContext();
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        return pref.getString(TOKEN_KEY, null);
    }

    public void setToken(String token) {
        Context context = GlobalContext.getInstance().getContext();
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public void removeToken() {
        Context context = GlobalContext.getInstance().getContext();
        SharedPreferences pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.remove(TOKEN_KEY);
        editor.commit();
    }
}
