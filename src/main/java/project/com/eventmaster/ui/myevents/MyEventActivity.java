package project.com.eventmaster.ui.myevents;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.adapter.MyEventsAdapter;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.repository.UserRepository;

public class MyEventActivity extends AppCompatActivity implements UserRepository.OnFetchMyEventsResponse {

    @BindView(R.id.list_my_events) RecyclerView viewEvents;

    static final String TAG = "MyEvents";

    UserRepository repository;
    MyEventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        ButterKnife.bind(this);

        // enable back button
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        repository = new UserRepository();

        adapter = new MyEventsAdapter(Arrays.asList());
        viewEvents.setAdapter(adapter);
        viewEvents.setLayoutManager(new LinearLayoutManager(this));

        repository.setFetchMyEventsListener(this);
        repository.fetchMyEvents(); // fetch events from backend

        Log.d(TAG, "init activity");
    }

    @Override
    public void OnFetchMyEventsSuccess(List<Event> events) {
         adapter.setEvents(events);
        Log.d(TAG, "fetch successfully");
    }

    @Override
    public void OnFetchMyEventsFailed(Exception e) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
