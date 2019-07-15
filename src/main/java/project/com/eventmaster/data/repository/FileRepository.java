package project.com.eventmaster.data.repository;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.com.eventmaster.data.model.FileResponse;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.FileService;
import project.com.eventmaster.utils.FileUtils;
import project.com.eventmaster.utils.GlobalContext;
import project.com.eventmaster.utils.TokenHelper;

public class FileRepository {

    private FileService service;
    static FileRepository instance;

    public static FileRepository getInstance() {
        if (instance == null) {
            instance = new FileRepository();
        }
        return instance;
    }

    public FileRepository() {
        service = RetrofitClientInstance.getClient().create(FileService.class);
    }

    public Observable<FileResponse> uploadFile(File file, String type) {
        Context context = GlobalContext.getInstance().getContext();

        Uri uri = Uri.fromFile(file);

        // convert file to request body
        RequestBody body = RequestBody.create(
                MediaType.parse(type),
                file);

        // create part
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);

        String token = TokenHelper.getInstance().getToken();

        return Observable.create(emitter -> {
            service.upload(token, part)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FileResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(FileResponse fileResponse) {
                            emitter.onNext(fileResponse);
                        }

                        @Override
                        public void onError(Throwable e) {
                            emitter.onError(e);
                        }

                        @Override
                        public void onComplete() {
                            emitter.onComplete();
                        }
                    });
        });
    }
}
