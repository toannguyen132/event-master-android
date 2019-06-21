package project.com.eventmaster.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchEventsResponse {
    @SerializedName("total")
    Integer total;

    @SerializedName("results")
    List<Event> results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Event> getEvents() {
        return results;
    }

    public void setEvents(List<Event> events) {
        this.results = events;
    }
}
