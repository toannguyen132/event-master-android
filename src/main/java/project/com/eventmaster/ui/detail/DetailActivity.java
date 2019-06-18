package project.com.eventmaster.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.utils.DisplayHelper;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel viewModel;
    ImageView viewImage;
    TextView viewTitle, viewDesc;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // setup resource
        viewImage = findViewById(R.id.detail_event_image);
        viewTitle = findViewById(R.id.detail_event_name);
        viewDesc = findViewById(R.id.detail_event_desc);

        // retrieve data
        Gson gson = new Gson();
        String eventJson = getIntent().getStringExtra("event");
        Event event = gson.fromJson(eventJson, Event.class);

        // get view model
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        context = this;

        // bind data
        viewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event event) {
                String imageUri = DisplayHelper.getInstance().imageUri(event.getImage().getFilename());
                Picasso.get()
                        .load(imageUri)
                        .placeholder(R.drawable.splash)
                        .centerCrop()
                        .fit()
                        .into(viewImage);
                viewTitle.setText(event.getName());
                viewDesc.setText(event.getDescription());
            }
        });

        // set default event
        viewModel.setEvent(event);
    }
}
