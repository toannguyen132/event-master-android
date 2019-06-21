package project.com.eventmaster.services;

import java.util.List;

import io.reactivex.Observable;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.SearchEventsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventService {

    @GET("/api/event")
    Observable<SearchEventsResponse> getEvents(@Query("search") String search, @Query("page") Integer page);

}
