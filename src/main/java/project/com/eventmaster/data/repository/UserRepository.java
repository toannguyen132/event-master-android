package project.com.eventmaster.data.repository;

import android.util.Log;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.UserService;
import project.com.eventmaster.utils.TokenHelper;

public class UserRepository {
    static final String TAG = "User Repository";

    public static UserRepository instance;

    OnReadNotificationResponse readNotificationListener;
    OnFetchMyEventsResponse fetchMyEventsListener;
    UserService userService;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserRepository() {
        userService = RetrofitClientInstance.getClient().create(UserService.class);
    }

    public void setReadNotificationListener(OnReadNotificationResponse readNotificationListener) {
        this.readNotificationListener = readNotificationListener;
    }

    public void setFetchMyEventsListener(OnFetchMyEventsResponse fetchMyEventsListener) {
        this.fetchMyEventsListener = fetchMyEventsListener;
    }

    public void readNotification(String id) {
        final String token = TokenHelper.getInstance().getToken();
        userService.readNotification(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (readNotificationListener != null) {
                            readNotificationListener.onReadNotificationSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (readNotificationListener != null) {
                            readNotificationListener.onReadNotificationFailed(new Exception(e));
                        }
                    }
                });
    }

    public void fetchMyEvents() {
        final String token = TokenHelper.getInstance().getToken();

        Log.d(TAG, "fetch my events");

        userService.fetchMyEvents(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Event>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Event> events) {
                        Log.d(TAG, "successfully get events");
                        if (fetchMyEventsListener != null) {
                            fetchMyEventsListener.OnFetchMyEventsSuccess(events);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "fetch my event error " + e.getMessage());
                        if (fetchMyEventsListener != null) {
                            fetchMyEventsListener.OnFetchMyEventsFailed(new Exception(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface OnReadNotificationResponse {
        void onReadNotificationSuccess();
        void onReadNotificationFailed(Exception e);
    }

    public interface OnFetchMyEventsResponse {
        void OnFetchMyEventsSuccess(List<Event> events);
        void OnFetchMyEventsFailed(Exception e);
    }
}
