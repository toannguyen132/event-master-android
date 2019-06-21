package project.com.eventmaster.utils;

import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.com.eventmaster.Config;

public class DisplayHelper {

    private final String BASE_URL = Config.UPLOAD_URL;
    private final String ISO_DATE = "yyyy-MM-dd'T'HH:mm:ss.000'Z'";
    private final String DISPLAY_DATE = "MM/dd/yyyy";

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
        SimpleDateFormat format = new SimpleDateFormat(ISO_DATE);
        SimpleDateFormat displayFormat = new SimpleDateFormat(DISPLAY_DATE);

        try {
            Date dateObject = format.parse(date);
            return displayFormat.format(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
