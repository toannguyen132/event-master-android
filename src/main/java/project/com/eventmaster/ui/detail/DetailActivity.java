package project.com.eventmaster.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Coordinate;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.utils.DisplayHelper;
import project.com.eventmaster.utils.SharedPreferencesHelper;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    DetailViewModel viewModel;
    ImageView viewImage;
    TextView viewTitle, viewDesc, viewLocation, viewDate;
    private GoogleMap gmap;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // enable back button
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        // setup resource
        viewImage = findViewById(R.id.detail_event_image);
        viewTitle = findViewById(R.id.detail_event_name);
        viewDesc = findViewById(R.id.detail_event_desc);
        viewLocation = findViewById(R.id.detail_event_location);
        viewDate = findViewById(R.id.detail_event_date);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);

        // retrieve data
        Gson gson = new Gson();
        String eventJson = getIntent().getStringExtra("event");
        Event event = gson.fromJson(eventJson, Event.class);
        String eventId = getIntent().getStringExtra("eventId");

        // get view model
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        context = this;

        // bind datar
        viewModel.getEvent().observe(this, event1 -> {
            String imageUri = DisplayHelper.getInstance().imageUri(event1.getImage().getFilename());
            Picasso.get()
                    .load(imageUri)
                    .placeholder(R.drawable.splash)
                    .centerCrop()
                    .fit()
                    .into(viewImage);
            viewTitle.setText(event1.getName());
            viewDate.setText( "Date: " + DisplayHelper.getInstance().dateFormat(event1.getStartDate()));
            viewLocation.setText("Location" + event1.getLocation());
            viewDesc.setText(event1.getDescription());
            this.setTitle(event1.getName());

            // setup coordinate
            Coordinate coordinate = event1.getCoordinate();
            if (coordinate != null) {
                setupMap(coordinate.getLat(), coordinate.getLng(), event1.getName());
            }
        });


        // init map
        mapFragment.getMapAsync(this);

        // fetch event
        viewModel.fetchEvent(eventId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Update location
     * @param lat
     * @param lng
     */
    private void setupMap(double lat, double lng, String title) {
        LatLng loc = new LatLng(lat, lng);
        gmap.setMinZoomPreference(14);
        // create marker
        gmap.addMarker(new MarkerOptions().position(loc).title(title));
        // move map
        gmap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);

        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
