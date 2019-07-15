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

import project.com.eventmaster.R;
import project.com.eventmaster.data.model.Event;
import project.com.eventmaster.data.model.Image;
import project.com.eventmaster.utils.ActivityHelper;
import project.com.eventmaster.utils.DisplayHelper;

public class SearchedEventViewHolder extends RecyclerView.ViewHolder {

    private TextView textEventName;
    private ImageView imgThumb;
    private View view;

    public SearchedEventViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
        textEventName = itemView.findViewById(R.id.txt_event_name);
        imgThumb = itemView.findViewById(R.id.img_event_thumb);
        imgThumb.setImageResource(R.drawable.splash);
    }

    public static SearchedEventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new SearchedEventViewHolder(view);
    }

    public void bindTo(Event event) {

        Image image = event.getImages().get(0);
        textEventName.setText(event.getName());

        if (image != null) {
            Picasso.get().load(DisplayHelper.getInstance().imageUri(image.getFilename())).into(imgThumb);
        }

        /** open detail view **/
        view.setOnClickListener(view1 -> ActivityHelper.getInstance().openEventDetail(view1.getContext(), event.getId()));
    }
}
