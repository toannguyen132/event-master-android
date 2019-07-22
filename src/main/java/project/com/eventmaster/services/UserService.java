package project.com.eventmaster.services;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import project.com.eventmaster.data.model.AuthResponse;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.request.Login;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("/api/user/notification")
    Observable<CurrentUser> getProfile(@Header("x-access-token") String token);

    @PUT("/api/user/notification/{id}")
    Completable readNotification(@Header("x-access-token") String token, @Path("id") String id);

    @GET("/api/user/my-events")
    Observable<List<Event>> fetchMyEvents(@Header("x-access-token") String token);

    @POST("/api/user/subscribe/{id}")
    Completable subscribe(@Header("x-access-token") String token, @Path("id") String id);

    @DELETE("/api/user/subscribe/{id}")
    Completable unsubscribe(@Header("x-access-token") String token, @Path("id") String id);
}
