package project.com.eventmaster.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.Image;
import project.com.eventmaster.data.model.Ticket;
import project.com.eventmaster.utils.ActivityHelper;
import project.com.eventmaster.utils.DisplayHelper;

public class SearchedEventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_event_name) TextView textEventName;
    @BindView(R.id.txt_event_location) TextView textEventLocation;
    @BindView(R.id.img_event_thumb) ImageView imgThumb;
    @BindView(R.id.txt_event_month) TextView textEventMonth;
    @BindView(R.id.txt_event_day) TextView textEventDay;
    @BindView(R.id.txt_event_prices) TextView textEventPrices;
    private View view;

    public SearchedEventViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this, view);
    }

    public static SearchedEventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new SearchedEventViewHolder(view);
    }

    public void bindTo(Event event) {
        DisplayHelper helper = DisplayHelper.getInstance();

        Image image = event.getImages().get(0);
        textEventName.setText(event.getName());
        textEventLocation.setText(event.getLocation());
        textEventMonth.setText(helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_MONTH));
        textEventDay.setText(helper.dateFormat(event.getStartDate(), DisplayHelper.DISPLAY_DAY));
        if (event.getTickets().size() > 0) {
            textEventPrices.setText(Ticket.getPriceString(event.getTickets()));
        } else {
            textEventPrices.setVisibility(View.GONE);
        }

        if (image != null) {
            Picasso.get().load(DisplayHelper.getInstance().imageUri(image.getFilename())).into(imgThumb);
        }

        /** open detail view **/
        view.setOnClickListener(view1 -> ActivityHelper.getInstance().openEventDetail(view1.getContext(), event.getId()));
    }
}
