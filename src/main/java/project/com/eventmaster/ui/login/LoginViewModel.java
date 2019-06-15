package project.com.eventmaster.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import project.com.eventmaster.data.repository.LoginRepository;
import project.com.eventmaster.data.model.AuthResponse;
import project.com.eventmaster.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password)
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        loginResult.setValue(new LoginResult(new LoggedInUserView(authResponse.getToken(), authResponse.getEmail())));
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginResult.setValue(new LoginResult(R.string.login_failed));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                .setOnCompletedListener(new LoginRepository.OnCompletedListener<AuthResponse>() {
//                    @Override
//                    public void onCompleted(Result.Success<AuthResponse> result) {
//                        AuthResponse resp = result.getData();
//                        loginResult.setValue(new LoginResult(new LoggedInUserView(resp.getToken(), resp.getEmail())));
//                    }
//                })
//                .setOnFailedListener(new LoginRepository.OnFailuedListener() {
//                    @Override
//                    public void onFailed(String errorMessage) {
//                        loginResult.setValue(new LoginResult(R.string.login_failed));
//                    }
//                });

        /** original code
        Result<CurrentUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            CurrentUser data = ((Result.Success<CurrentUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
         */
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
