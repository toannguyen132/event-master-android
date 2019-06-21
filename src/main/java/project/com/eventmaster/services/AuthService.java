package project.com.eventmaster.services;

import io.reactivex.Observable;
import project.com.eventmaster.data.model.AuthResponse;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.model.RegisterRequest;
import project.com.eventmaster.data.model.RegisterResponse;
import project.com.eventmaster.data.request.Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/auth/login")
    Observable<AuthResponse> login(@Body Login loginData);

    @POST("/api/auth/register")
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @POST("/api/auth/logout")
    Call<Void> logout();

    @GET("/api/user/profile")
    Observable<CurrentUser> getProfile(@Header("x-access-token") String token);
    //Call<CurrentUser> getProfile(@Header("x-access-token") String token);
}
