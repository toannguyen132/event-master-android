package project.com.eventmaster.services;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import project.com.eventmaster.data.model.FileResponse;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileService {
    @Multipart
    @POST("/api/upload/event")
    Observable<FileResponse> upload(
            @Header("x-access-token") String token,
            @Part MultipartBody.Part file
    );
}
