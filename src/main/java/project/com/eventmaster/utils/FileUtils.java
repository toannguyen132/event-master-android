package project.com.eventmaster.utils;

import android.net.Uri;

import java.io.File;

public class FileUtils {

    static FileUtils instance;

    static public FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    public FileUtils() { }


    public File fromUri(Uri uri) {
        return new File(uri.getPath());
    }
}
