package project.com.eventmaster.ui.splash;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.repository.LoginRepository;

public class SplashViewModel extends ViewModel {

    private LoginRepository repository;

    private MutableLiveData<CurrentUser> currentUser = new MutableLiveData<>();
    private MutableLiveData<Result> getProfileResult = new MutableLiveData<>();

    LiveData<CurrentUser> getCurrentUser() {return this.currentUser; }
    LiveData<Result> getProfileResult() {return this.getProfileResult; }

    public SplashViewModel(LoginRepository repository) {
        this.repository = repository;
    }

    public void fetchProfile() {
        repository.getProfile().subscribe(new Observer<CurrentUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CurrentUser user) {
                currentUser.setValue(user);
            }

            @Override
            public void onError(Throwable e) {
                getProfileResult.setValue(new Result.Error(new Exception(e)));
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
