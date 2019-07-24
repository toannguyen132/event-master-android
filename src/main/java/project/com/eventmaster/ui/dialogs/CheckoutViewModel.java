package project.com.eventmaster.ui.dialogs;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import project.com.eventmaster.data.model.Ticket;

public class CheckoutViewModel extends ViewModel {
    MutableLiveData<List<Ticket>> tickets = new MutableLiveData<>();


}
