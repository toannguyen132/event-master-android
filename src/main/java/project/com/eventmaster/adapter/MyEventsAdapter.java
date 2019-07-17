package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import project.com.eventmaster.data.model.Event;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventViewHolder> {

    List<Event> events;

    public MyEventsAdapter(List<Event> events) {
        this.events = events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return MyEventViewHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventViewHolder myEventViewHolder, int i) {
        myEventViewHolder.bind(events.get(i));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
