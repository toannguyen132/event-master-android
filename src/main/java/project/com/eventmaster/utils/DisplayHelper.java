package project.com.eventmaster.utils;

import android.content.res.Resources;

import project.com.eventmaster.Config;
import project.com.eventmaster.R;

public class DisplayHelper {

    private final String BASE_URL = Config.UPLOAD_URL;

    static DisplayHelper instance;
    static public DisplayHelper getInstance() {
        if (instance == null) {
            instance = new DisplayHelper();
        }
        return instance;
    }

    public String imageUri(String filename) {
        return BASE_URL + filename;
    }

    public String dateFormat(String date) {
        return date;
    }
}
