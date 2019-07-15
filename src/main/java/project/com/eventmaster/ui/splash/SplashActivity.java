package project.com.eventmaster.ui.splash;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import project.com.eventmaster.R;
import project.com.eventmaster.data.Result;
import project.com.eventmaster.data.model.Subscription;
import project.com.eventmaster.ui.login.LoginActivity;
import project.com.eventmaster.ui.main.MainActivity;
import project.com.eventmaster.utils.GlobalContext;
import project.com.eventmaster.utils.TokenHelper;

public class SplashActivity extends AppCompatActivity {
    final static String TAG = "Splash";

    final static int LOGIN_REQUEST = 1;
    final static int MAIN_REQUEST = 2;

    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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

            // register notification
            notification(getSubscriptionStrings(currentUser.getSubscriptions()));

            //
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

//        notification();

    }

    private void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST);
    }

    private void moveToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, MAIN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST ) {
            if (resultCode == Activity.RESULT_OK){
                splashViewModel.fetchProfile();
            }
            else
                moveToLogin();
        }
        if (requestCode == MAIN_REQUEST) {
            moveToLogin();
        }
    }

    private List<String> getSubscriptionStrings(List<Subscription> subs) {
        List<String> result = new ArrayList<>();

        for (Subscription sub : subs) {
            result.add(sub.getId());
        }
        return result;
    }

    private void notification(List<String> catIds) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.notification_channel);
            String channelName = getString(R.string.notification_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));

            Log.d(TAG, "subscribe to topic");

            FirebaseMessaging instance = FirebaseMessaging.getInstance();

            for (String catId : catIds) {
                instance.subscribeToTopic(catId)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Log.d(TAG, "subscribe to " + catId);
                            } else {
                                Log.d(TAG, "subscribe failed");
                            }
                        });
            }
            instance.subscribeToTopic("music")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Log.d(TAG, "subscribe to music" );
                        } else {
                            Log.d(TAG, "subscribe failed");
                        }
                    });
        }
    }
}
