package project.com.eventmaster.ui.dialogs;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import project.com.eventmaster.data.model.Ticket;

public class CheckoutViewModel extends ViewModel {
    MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();
    MutableLiveData<CheckoutData> checkoutData = new MutableLiveData<>();

    public MutableLiveData<List<Ticket>> getTickets() {
        return tickets;
    }

    public MutableLiveData<CheckoutData> getCheckoutData() {
        return checkoutData;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets.setValue(tickets);
    }

    public void setCheckoutData(CheckoutData data) {
        data.calculate();
        this.checkoutData.setValue(data);
    }

    public void updateCheckoutTicket(Ticket ticket) {
        if (ticket != null) {
            CheckoutData data = checkoutData.getValue();
            data.setTicket(ticket);
            this.setCheckoutData(data);
        }
    }

    public void updateCheckoutQuantity(int quantity) {
        CheckoutData data = checkoutData.getValue();
        data.setQuantity(quantity);
        this.setCheckoutData(data);
    }
}
