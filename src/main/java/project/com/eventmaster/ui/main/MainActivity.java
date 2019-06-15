package project.com.eventmaster.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import project.com.eventmaster.R;
import project.com.eventmaster.ui.fragments.eventlist.EventListFragment;
import project.com.eventmaster.utils.TokenHelper;

public class MainActivity extends AppCompatActivity implements EventListFragment.OnFragmentInteractionListener {
    private TextView mTextMessage;

    EventListFragment eventListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String token = TokenHelper.getInstance().getToken();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (eventListFragment == null) {
                        eventListFragment = EventListFragment.newInstance("", "");
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_events, eventListFragment).commit();

                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        // fragment
        EventListFragment fragment = EventListFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_events, fragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
