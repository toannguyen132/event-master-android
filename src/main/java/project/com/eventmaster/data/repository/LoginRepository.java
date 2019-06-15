package project.com.eventmaster.data.repository;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.com.eventmaster.data.LoginDataSource;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.AuthResponse;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.request.LoginData;
import project.com.eventmaster.network.RetrofitClientInstance;
import project.com.eventmaster.services.AuthService;
import project.com.eventmaster.utils.TokenHelper;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    OnCompletedListener completedListener;
    OnFailuedListener failuedListener;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private CurrentUser user = null;

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    private void setCurrentUser(CurrentUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public CurrentUser getUser() {
        return user;
    }

    /**
     * Perform action login
     *
     * @param email
     * @param password
     * @return
     */
    public Observable<AuthResponse> login(String email, String password) {
        AuthService service = RetrofitClientInstance.getClient().create(AuthService.class);

        return service.login(new LoginData(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get current user profile
     * @return
     */
    public Observable<CurrentUser> getProfile() {

        final String token = TokenHelper.getInstance().getToken();

        final AuthService service = RetrofitClientInstance.getClient().create(AuthService.class);


        return Observable.create(new ObservableOnSubscribe<CurrentUser>() {
            @Override
            public void subscribe(final ObservableEmitter<CurrentUser> emitter) throws Exception {
                service.getProfile(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CurrentUser>() {
                            @Override
                            public void onSubscribe(Disposable d) {}

                            @Override
                            public void onNext(CurrentUser currentUser) {
                                // set current user
                                setCurrentUser(currentUser);
                                //
                                emitter.onNext(currentUser);
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
            }
        });

//        return service.getProfile(token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
    }

    public LoginRepository setOnCompletedListener(OnCompletedListener listener) {
        this.completedListener = listener;
        return this;
    }
    public LoginRepository setOnFailedListener(OnFailuedListener listener) {
        this.failuedListener = listener;
        return this;
    }

    public interface OnCompletedListener<T> {
        void onCompleted(Result.Success<T> resp);
    }

    public interface OnFailuedListener {
        void onFailed(String errorMessage);
    }

}
