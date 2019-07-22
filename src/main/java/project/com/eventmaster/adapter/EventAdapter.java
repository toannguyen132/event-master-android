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

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.Image;
import project.com.eventmaster.data.model.Ticket;
import project.com.eventmaster.utils.ActivityHelper;
import project.com.eventmaster.utils.DisplayHelper;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> events;

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
        viewHolder.bind(event);
    }

    @Override
    public int getItemCount() {
        if (events != null) {
            return events.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_event_name) TextView textEventName;
        @BindView(R.id.txt_event_location) TextView textEventLocation;
        @BindView(R.id.img_event_thumb) ImageView imgThumb;
        @BindView(R.id.txt_event_month) TextView textEventMonth;
        @BindView(R.id.txt_event_day) TextView textEventDay;
        @BindView(R.id.txt_event_prices) TextView textEventPrices;

        private Context context;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            view = itemView;
            ButterKnife.bind(view);
        }

        public void bind(Event event) {
            DisplayHelper helper = DisplayHelper.getInstance();
            Image image = event.getImages().get(0);
            textEventName.setText(event.getName());
            textEventLocation.setText(event.getLocation());
            textEventMonth.setText(helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_MONTH));
            textEventDay.setText(helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_DAY));
            textEventPrices.setText(Ticket.getPriceString(event.getTickets()));

            if (image != null) {
                Picasso.get().load(DisplayHelper.getInstance().imageUri(image.getFilename())).into(imgThumb);
            }

            /** open detail view **/
            view.setOnClickListener(view1 -> ActivityHelper.getInstance().openEventDetail(view1.getContext(), event));
        }
    }
}
