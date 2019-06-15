package project.com.eventmaster.utils;

import android.content.Context;

public class GlobalContext {

    static GlobalContext instance;

    private Context context;

    static public GlobalContext getInstance() {
        return instance;
    }

    static public void setInstance(Context context) {
        instance = new GlobalContext(context);
    }

    private GlobalContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
