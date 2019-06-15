package project.com.eventmaster.ui.splash;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import project.com.eventmaster.data.repository.LoginRepository;
import project.com.eventmaster.ui.login.LoginViewModel;

public class SplashViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(LoginRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
