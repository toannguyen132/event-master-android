package project.com.eventmaster.ui.dialogs;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.adapter.TicketSpinnerAdapter;
import project.com.eventmaster.data.model.Ticket;
import project.com.eventmaster.ui.detail.DetailViewModel;

public class CheckoutDialog extends Dialog implements View.OnClickListener{
    private static final int BRAINTREE_REQUEST_CODE = 1;
    private static final String TAG = "Checkout Dialog";

    @BindView(R.id.checkout_submit)
    Button btnSubmit;
    @BindView(R.id.checkout_ticket)
    Spinner ticketSpinner;
    @BindView(R.id.checkout_quantity)
    Spinner quantitySpinner;
    @BindView(R.id.checkout_subtotal)
    TextView subtotalText;
    @BindView(R.id.checkout_tax)
    TextView taxText;
    @BindView(R.id.checkout_total)
    TextView totalText;

    String nonce;
    String eventId;
    Context context;

    String token;
    OnCheckoutListener listener;

    // view model
    CheckoutViewModel mViewModel;

    FragmentActivity parent;

    // adapter
    TicketSpinnerAdapter ticketSpinnerAdapter;

    public CheckoutDialog(Context context, FragmentActivity activity) {
        super(context);
        this.context = context;
        this.parent = activity;

        this.setContentView(R.layout.dialog_checkout);
        ButterKnife.bind(this);

        initViewModel();
        initSpinners();
        initEvents();
    }

    public void setTickets(List<Ticket> tickets) {
        mViewModel.setTickets(tickets);
    }

    private void initEvents(){
        btnSubmit.setOnClickListener(this);
        ticketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "position " + i);
                Ticket ticket = mViewModel.getTickets().getValue().get(i);
                mViewModel.updateCheckoutTicket(ticket);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int quantity = i+1;
                mViewModel.updateCheckoutQuantity(quantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinners() {
        ticketSpinnerAdapter = new TicketSpinnerAdapter(context, Arrays.asList());
        ticketSpinner.setAdapter(ticketSpinnerAdapter);

        String[] quantities = new String[] {"1", "2", "3", "4"};
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(quantities)));
        quantitySpinner.setAdapter(quantityAdapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(parent).get(CheckoutViewModel.class);
        mViewModel.getTickets().observe(parent, new Observer<List<Ticket>>() {
            @Override
            public void onChanged(@Nullable List<Ticket> tickets) {
                ticketSpinnerAdapter.setTickets(tickets);
                if (tickets.size() > 0) mViewModel.updateCheckoutTicket(tickets.get(0));
            }
        });
        mViewModel.getCheckoutData().observe(parent, new Observer<CheckoutData>() {
            @Override
            public void onChanged(@Nullable CheckoutData checkoutData) {
                totalText.setText("$" + checkoutData.getTotal());
                taxText.setText("$" + checkoutData.getTax() + "");
                subtotalText.setText("$" + checkoutData.getSubtotal() + "");
            }
        });
        CheckoutData data = new CheckoutData();
        data.setTicket(null);
        data.setQuantity(1);
        mViewModel.setCheckoutData(data);
    }

    public void setOnCheckoutListener(OnCheckoutListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onCheckout(mViewModel.getCheckoutData().getValue());
        }

    }

    public interface OnCheckoutListener {
        public void onCheckout(CheckoutData checkoutData);
    }
}
