package project.com.eventmaster.adapter;

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
import project.com.eventmaster.utils.DisplayHelper;

public class MyEventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.my_event_name) TextView textName;
    @BindView(R.id.my_event_location) TextView textLocation;
    @BindView(R.id.my_event_date) TextView textDate;
    @BindView(R.id.my_event_image) ImageView imgAvatar;

    public static MyEventViewHolder create(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_event, viewGroup, false);
        return new MyEventViewHolder(view);
    }

    public MyEventViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Event event) {
        textName.setText(event.getName());
        textLocation.setText(event.getLocation());
        textDate.setText(DisplayHelper.getInstance().dateFormat(event.getStartDate()));
        Image image = event.getImages().get(0);
        if (image != null) {
            Picasso.get().load(DisplayHelper.getInstance().imageUri(image.getFilename())).into(imgAvatar);
        }
    }
}
