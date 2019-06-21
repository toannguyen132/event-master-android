package project.com.eventmaster.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import project.com.eventmaster.R;
import project.com.eventmaster.ui.fragments.MenuFragment;
import project.com.eventmaster.ui.fragments.createevent.CreateEventFragment;
import project.com.eventmaster.ui.fragments.eventlist.EventListFragment;
import project.com.eventmaster.ui.login.LoginActivity;
import project.com.eventmaster.utils.TokenHelper;

public class MainActivity extends AppCompatActivity implements EventListFragment.OnFragmentInteractionListener, MenuFragment.OnLogoutListener {
    private TextView mTextMessage;

    private int LOGIN_REQUEST = 1;

    EventListFragment eventListFragment;
    CreateEventFragment createEventFragment;
    MenuFragment menuFragment;

    private final static int SCREEN_HOME = 0;
    private final static int SCREEN_CREATE = 1;
    private final static int SCREEN_PROFILE = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        openScreen(SCREEN_HOME);
                        return true;
                    case R.id.navigation_create:
                        openScreen(SCREEN_CREATE);
                        return true;
                    case R.id.navigation_menu:
                        openScreen(SCREEN_PROFILE);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // fragments
        setupFragments();
        openHomeScreen();
        switch (navView.getSelectedItemId()){
            case R.id.navigation_home:
                openScreen(SCREEN_HOME);
                break;
            case R.id.navigation_create:
                openScreen(SCREEN_CREATE);
                break;
            case R.id.navigation_menu:
                openScreen(SCREEN_PROFILE);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void setupFragments() {
        if (eventListFragment == null) {
            eventListFragment = EventListFragment.newInstance("", "");
        }
        if (createEventFragment == null) {
            createEventFragment = CreateEventFragment.newInstance();
        }
        if (menuFragment == null) {
            menuFragment = MenuFragment.newInstance();
            menuFragment.setOnLogoutListener(this);
        }
    }

    private void openScreen(int screen) {
        if (screen == SCREEN_HOME) {
            openHomeScreen();
        } else if (screen == SCREEN_CREATE) {
            openCreateScreen();
        } else if (screen == SCREEN_PROFILE) {
            openProfileScreen();
        }
    }

    private void openHomeScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_events, eventListFragment).commit();
    }

    private void openCreateScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_events, createEventFragment).commit();
    }

    private void openProfileScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_events, menuFragment).commit();
    }

    @Override
    public void onLogout() {
        TokenHelper.getInstance().removeToken();
        setResult(Activity.RESULT_OK);
        finish();
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivityForResult(intent, LOGIN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST && resultCode == Activity.RESULT_OK) {

        } else {

        }
    }
}
