package project.com.eventmaster.ui.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.RegisterRequest;
import project.com.eventmaster.data.model.RegisterResponse;
import project.com.eventmaster.data.repository.LoginRepository;
import retrofit2.HttpException;

public class RegisterViewModel extends ViewModel {

    MutableLiveData<Result> registerResult = new MutableLiveData<>();

    LoginRepository loginRepository;

    public RegisterViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public MutableLiveData<Result> getRequestResult() {
        return registerResult;
    }

    public void register(RegisterRequest request) {
        loginRepository.register(request)
                .subscribe(new Observer<RegisterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResponse registerResponse) {
                        registerResult.setValue(new Result.Success<RegisterResponse>(registerResponse));
                    }

                    @Override
                    public void onError(Throwable e) {
                        registerResult.setValue(new Result.Error(new Exception(e)));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
