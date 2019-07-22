package project.com.eventmaster.services;

import java.util.List;

import io.reactivex.Observable;
import project.com.eventmaster.data.model.Category;
import project.com.eventmaster.data.model.CreateEventRequest;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.SearchEventsResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventService {

    @GET("/api/event")
    Observable<SearchEventsResponse> getEvents(@Query("search") String search, @Query("page") Integer page);

    @GET("/api/event/{id}")
    Observable<Event> getSingleEvent(@Path("id") String id);

    @POST("/api/event")
    Observable<Event> createEvent(@Header("x-access-token") String token, @Body CreateEventRequest request);

    @GET("/api/event/category")
    Observable<List<Category>> fetchCategories(@Header("x-access-token") String token);

}
