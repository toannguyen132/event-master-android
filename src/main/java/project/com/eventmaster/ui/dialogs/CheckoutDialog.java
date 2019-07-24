package project.com.eventmaster.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Ticket;

public class CheckoutDialog extends Dialog implements View.OnClickListener{
    private static final int BRAINTREE_REQUEST_CODE = 1;

    @BindView(R.id.checkout_submit)
    Button btnSubmit;

    String nonce;
    String eventId;
    List<Ticket> tickets;
    Context context;

    String token;
    OnCheckoutListener listener;

    public CheckoutDialog(Context context) {
        super(context);
        this.context = context;

        this.setContentView(R.layout.dialog_checkout);
        ButterKnife.bind(this);


        btnSubmit.setOnClickListener(this);
    }

    public void setOnCheckoutListener(OnCheckoutListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        if (listener != null) {
            listener.onCheckout();
        }

    }

    public interface OnCheckoutListener {
        public void onCheckout();
    }
}
