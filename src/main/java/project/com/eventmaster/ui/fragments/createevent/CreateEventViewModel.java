package project.com.eventmaster.ui.fragments.createevent;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.Category;
import project.com.eventmaster.data.model.CreateEventRequest;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.FileResponse;
import project.com.eventmaster.data.repository.EventRepository;
import project.com.eventmaster.data.repository.FileRepository;
import project.com.eventmaster.utils.ErrorResponse;
import retrofit2.HttpException;

public class CreateEventViewModel extends ViewModel {
    private FileRepository fileRepository;
    private EventRepository eventRepository;
    private MutableLiveData<FileResponse> fileResponse = new MutableLiveData<>();
    private MutableLiveData<Result.Error> error = new MutableLiveData<>();
    private MutableLiveData<Result.Success<Event>> createResult = new MutableLiveData<>();
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    private CreateEventRequest eventRequest;

    public CreateEventViewModel() {
        fileRepository = FileRepository.getInstance();
        eventRepository = EventRepository.getInstance();
    }

    public CreateEventRequest getEventRequest() {
        return eventRequest;
    }

    public LiveData<Result.Success<Event>> getCreateResult() {
        return createResult;
    }

    public void setEventRequest(CreateEventRequest eventRequest) {
        this.eventRequest = eventRequest;
    }

    public void setEventImage(String image) {
        eventRequest.setImage(Arrays.asList(new String[] {image}));
    }

    public LiveData<Result.Error> getError() {
        return error;
    }

    public LiveData<FileResponse> getFileResponse() {
        return fileResponse;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public void uploadFile(File file, String type) {
        fileRepository.uploadFile(file, type).subscribe(new Observer<FileResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FileResponse response) {
                fileResponse.setValue(response);
            }

            @Override
            public void onError(Throwable e) {
//                error.setValue(ErrorResponse.get(e));
                error.setValue(new Result.Error( (Exception) e));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void create(CreateEventRequest request) {
        eventRepository.create(request)
                .subscribe(new Observer<Event>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Event event) {
                        createResult.setValue(new Result.Success(event));
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException)e).response().errorBody();
                            try {
                                JSONObject jsonObject = new JSONObject(body.string());
                                error.setValue(new Result.Error(new Exception( jsonObject.getString("message") )));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * fetch categories list
     */
    public void fetchCategories() {
        eventRepository.getCategories(new EventRepository.FetchCategoryListener() {
            @Override
            public void onFetchCategorySuccess(List<Category> categoriesList) {
                categories.setValue(categoriesList);
            }

            @Override
            public void onFetchCategoryFailed(Exception e) {

            }
        });
    }

}
