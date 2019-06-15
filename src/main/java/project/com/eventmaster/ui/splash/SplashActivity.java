package project.com.eventmaster.ui.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import project.com.eventmaster.R;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.ui.login.LoginActivity;
import project.com.eventmaster.ui.main.MainActivity;
import project.com.eventmaster.utils.GlobalContext;
import project.com.eventmaster.utils.TokenHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashViewModel splashViewModel;

        // initial global context
        GlobalContext.setInstance(getApplicationContext());

        // init view model
        splashViewModel = ViewModelProviders.of(this, new SplashViewModelFactory())
                .get(SplashViewModel.class);

        // init livedata
        splashViewModel.getProfileResult().observe(this, result -> {
            // move to login if cannot fetch the current user
            if (result instanceof Result.Error){
                moveToLogin();
            }
        });

        splashViewModel.getCurrentUser().observe(this, currentUser -> {
            Toast.makeText(getApplicationContext(), "Current user: " + currentUser.getName(), Toast.LENGTH_LONG).show();
            moveToMain();
        });

        // detect token
        String token = TokenHelper.getInstance().getToken();

        if (token == null) {
            moveToLogin();
        } else {
            // fetch profile if token is not null
            splashViewModel.fetchProfile();
        }

    }

    private void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void moveToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
