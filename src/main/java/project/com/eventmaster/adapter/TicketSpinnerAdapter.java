package project.com.eventmaster.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.com.eventmaster.data.model.Ticket;

public class TicketSpinnerAdapter extends ArrayAdapter<String> {

    List<Ticket> tickets;

    public TicketSpinnerAdapter(Context context, List<Ticket> tickets) {
        super(context, android.R.layout.simple_list_item_1, convertString(tickets));
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
        this.clear();
        this.addAll(convertString(tickets));
        this.notifyDataSetChanged();
    }

    public static ArrayList<String> convertString(List<Ticket> tickets){
        if (tickets.size() == 0) {
            return new ArrayList<>(Arrays.asList(new String[]{"Please select"}));
        }

        ArrayList<String> strlist = new ArrayList<>();
        for(Ticket ticket : tickets) {
            strlist.add(ticket.getName() + "($" + ticket.getPrice() + ")");
        }

        return strlist;
    }
}
