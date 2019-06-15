package project.com.eventmaster.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.Image;
import project.com.eventmaster.utils.DisplayHelper;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_event, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Event event = events.get(i);
        Image image = event.getImages().get(0);
        viewHolder.textEventName.setText(event.getName());
        if (image != null) {
            Picasso.with( viewHolder.context ).load(DisplayHelper.getInstance().imageUri(image.getFilename())).into(viewHolder.imgThumb);
        }
    }

    @Override
    public int getItemCount() {
        if (events != null) {
            return events.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textEventName;
        ImageView imgThumb;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            textEventName = itemView.findViewById(R.id.txt_event_name);
            imgThumb = itemView.findViewById(R.id.img_event_thumb);
            imgThumb.setImageResource(R.drawable.splash);
        }
    }
}
