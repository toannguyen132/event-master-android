package project.com.eventmaster.ui.detail;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Coordinate;
import project.com.eventmaster.data.model.CurrentUser;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.PaymentRequest;
import project.com.eventmaster.data.model.Ticket;
import project.com.eventmaster.data.repository.EventRepository;
import project.com.eventmaster.ui.dialogs.CheckoutData;
import project.com.eventmaster.ui.dialogs.CheckoutDialog;
import project.com.eventmaster.utils.DisplayHelper;
import project.com.eventmaster.utils.SharedPreferencesHelper;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, CheckoutDialog.OnCheckoutListener {

    private static final int BRAINTREE_REQUEST_CODE = 1;
    private static final String TAG = "DetailActivity";
    DetailViewModel viewModel;
    @BindView(R.id.detail_event_image) ImageView viewImage;
    @BindView(R.id.detail_event_name) TextView viewTitle;
    @BindView(R.id.detail_event_desc) TextView viewDesc;
    @BindView(R.id.detail_event_location) TextView viewLocation;
    @BindView(R.id.detail_event_month) TextView viewMonth;
    @BindView(R.id.detail_event_day) TextView viewDay;
    @BindView(R.id.detail_event_time) TextView viewTime;
    @BindView(R.id.detail_event_prices) TextView viewPrices;
    @BindView(R.id.detail_event_buy) Button btnBuy;

    private GoogleMap gmap;

    Context context;
    String token;
    String eventId;
    String ticketId;
    int quantity;

    CheckoutDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // enable back button
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        // bind view
        ButterKnife.bind(this);

        // retrieve data
        eventId = getIntent().getStringExtra("eventId");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        // init map
        mapFragment.getMapAsync(this);


        context = this;

        setViewModel();


        setDialog();
    }

    private void setDialog() {
        dialog = new CheckoutDialog(this, this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCheckoutListener(this);

    }

    private void setViewModel() {
        // get view model
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        // bind datar
        viewModel.getEvent().observe(this, event1 -> updateUi(event1));
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
        if (gmap != null) {
            LatLng loc = new LatLng(lat, lng);
            gmap.setMinZoomPreference(14);
            // create marker
            gmap.addMarker(new MarkerOptions().position(loc).title(title));
            // move map
            gmap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);

        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    private void updateUi(Event event) {
        String imageUri = DisplayHelper.getInstance().imageUri(event.getImage().getFilename());
        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.splash)
                .centerCrop()
                .fit()
                .into(viewImage);
        viewTitle.setText(event.getName());
//        viewDate.setText( "Date: " + DisplayHelper.getInstance().dateFormat(event.getStartDate()));
        viewLocation.setText("Location" + event.getLocation());
        viewDesc.setText(event.getDescription());

        DisplayHelper helper = DisplayHelper.getInstance();
        viewMonth.setText( helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_MONTH) );
        viewDay.setText( helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_DAY) );
        viewTime.setText( helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_TIME) );

        if (event.getTickets().size() == 0) {
            viewPrices.setVisibility(View.GONE);
            btnBuy.setVisibility(View.GONE);
        } else {
            viewPrices.setVisibility(View.VISIBLE);
            btnBuy.setVisibility(View.VISIBLE);
            viewPrices.setText(Ticket.getPriceString(event.getTickets()));
            btnBuy.setOnClickListener(this);
        }

        // setup tickets list
        dialog.setTickets(event.getTickets());

        this.setTitle(event.getName());

        // setup coordinate
        Coordinate coordinate = event.getCoordinate();
        if (coordinate != null) {
            setupMap(coordinate.getLat(), coordinate.getLng(), event.getName());
        }

    }

    @Override
    public void onCheckout(CheckoutData checkoutData) {
        token = getString(R.string.braintree_token);
        ticketId = checkoutData.getTicket().getId();
        quantity = checkoutData.getQuantity();

        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(context), BRAINTREE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BRAINTREE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                String nonce = result.getPaymentMethodNonce().getNonce();
                Log.d(TAG, "nonce received: " + nonce);

                // TODO: update ticket id and quantity
                submitPayment(nonce, eventId, ticketId, quantity); // static ticketid ObjectId("5d158d1f6789ce40aadb9a72")

            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled
                Log.d(TAG, "Cancel");
                dialog.dismiss();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    @Override
    public void onClick(View view) {
        dialog.show();
    }

    private void submitPayment(String nonce, String eventId, String ticketId, int quantity) {
        CurrentUser user = CurrentUser.getFromCache();
        String name = "";
        String address = "";
        if (user != null) {
            name = user.getName();
            address = user.getAddress() == null ? "sample address" : user.getAddress();
        }

        PaymentRequest request = PaymentRequest.create(nonce, eventId, ticketId, quantity, name, address);
        Log.d(TAG, "checkout ticket " + ticketId);
        viewModel.checkout(request, new EventRepository.CreatePaymentListener() {
            @Override
            public void onCreatePaymentSuccess() {
                Log.d(TAG, "Create transaction success");
                Toast.makeText(context, "Create transaction success", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void onCreatePaymentFailed(Exception e) {
                Log.d(TAG, "Create transaction failed!");
                Toast.makeText(context, "Create transaction failed", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }
}
