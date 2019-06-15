package project.com.eventmaster.services;

import java.util.List;

import io.reactivex.Observable;
import project.com.eventmaster.data.model.Event;
import retrofit2.http.GET;

public interface EventService {

    @GET("/api/event")
    Observable<List<Event>> getEvents();

}
