package project.com.eventmaster.services;

import io.reactivex.Completable;
import project.com.eventmaster.data.model.PaymentRequest;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PaymentService {
    @POST("/api/payment")
    Completable createPayment(@Header("x-access-token") String token, @Body PaymentRequest request);
}
