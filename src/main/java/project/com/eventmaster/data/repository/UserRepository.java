package project.com.eventmaster.data.repository;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.UserService;
import project.com.eventmaster.utils.TokenHelper;

public class UserRepository {

    public static UserRepository instance;

    OnReadNotificationResponse readNotificationListender;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void setReadNotificationListender(OnReadNotificationResponse readNotificationListender) {
        this.readNotificationListender = readNotificationListender;
    }

    public void readNotification(String id) {
        final String token = TokenHelper.getInstance().getToken();
        UserService userService = RetrofitClientInstance.getClient().create(UserService.class);

        userService.readNotification(token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (readNotificationListender != null) {
                            readNotificationListender.onReadNotificationSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (readNotificationListender != null) {
                            readNotificationListender.onReadNotificationFailed(new Exception(e));
                        }
                    }
                });
    }

    public interface OnReadNotificationResponse {
        void onReadNotificationSuccess();
        void onReadNotificationFailed(Exception e);
    }
}
