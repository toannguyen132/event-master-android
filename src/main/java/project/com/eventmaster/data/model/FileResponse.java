package project.com.eventmaster.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FileResponse {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
